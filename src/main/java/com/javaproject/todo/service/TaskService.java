package com.javaproject.todo.service;

import com.javaproject.todo.dto.TaskRequest;
import com.javaproject.todo.dto.TaskResponse;
import com.javaproject.todo.model.Task;
import com.javaproject.todo.model.TaskCategory;
import com.javaproject.todo.model.TaskPriority;
import com.javaproject.todo.model.User;
import com.javaproject.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public TaskResponse createTask(TaskRequest taskRequest, User user) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate()); // This will now use the ZonedDateTime method
        task.setPriority(taskRequest.getPriority());
        task.setCategory(taskRequest.getCategory());
        task.setUser(user);
        task.setCompleted(false);
        
        Task savedTask = taskRepository.save(task);
        return convertToTaskResponse(savedTask);
    }
    
    public TaskResponse getTaskById(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to access this task");
        }
        
        return convertToTaskResponse(task);
    }
    
    public List<TaskResponse> getAllTasksForUser(User user) {
        List<Task> tasks = taskRepository.findByUser(user);
        return tasks.stream()
                .map(this::convertToTaskResponse)
                .collect(Collectors.toList());
    }
    
    public Page<TaskResponse> getTasksWithFiltersAndSorting(
            User user, 
            Boolean completed, 
            TaskCategory category, 
            TaskPriority priority, 
            String keyword, 
            int page, 
            int size, 
            String sortBy, 
            String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Task> tasks;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            tasks = getTasksWithKeyword(user, completed, category, priority, keyword, pageable);
        } else {
            tasks = getTasksWithoutKeyword(user, completed, category, priority, pageable);
        }
        
        return tasks.map(this::convertToTaskResponse);
    }
    
    private Page<Task> getTasksWithKeyword(User user, Boolean completed, TaskCategory category, 
                                         TaskPriority priority, String keyword, Pageable pageable) {
        if (completed != null && category != null && priority != null) {
            return taskRepository.findByUserAndCompletedAndCategoryAndPriorityAndKeyword(
                    user, completed, category, priority, keyword, pageable);
        } else if (completed != null && category != null) {
            return taskRepository.findByUserAndCompletedAndCategoryAndKeyword(
                    user, completed, category, keyword, pageable);
        } else if (completed != null && priority != null) {
            return taskRepository.findByUserAndCompletedAndPriorityAndKeyword(
                    user, completed, priority, keyword, pageable);
        } else if (category != null && priority != null) {
            return taskRepository.findByUserAndCategoryAndPriorityAndKeyword(
                    user, category, priority, keyword, pageable);
        } else if (completed != null) {
            return taskRepository.findByUserAndCompletedAndKeyword(user, completed, keyword, pageable);
        } else if (category != null) {
            return taskRepository.findByUserAndCategoryAndKeyword(user, category, keyword, pageable);
        } else if (priority != null) {
            return taskRepository.findByUserAndPriorityAndKeyword(user, priority, keyword, pageable);
        } else {
            return taskRepository.findByUserAndKeyword(user, keyword, pageable);
        }
    }
    
    private Page<Task> getTasksWithoutKeyword(User user, Boolean completed, TaskCategory category, 
                                             TaskPriority priority, Pageable pageable) {
        if (completed != null && category != null && priority != null) {
            return taskRepository.findByUserAndCompletedAndCategoryAndPriority(
                    user, completed, category, priority, pageable);
        } else if (completed != null && category != null) {
            return taskRepository.findByUserAndCompletedAndCategory(user, completed, category, pageable);
        } else if (completed != null && priority != null) {
            return taskRepository.findByUserAndCompletedAndPriority(user, completed, priority, pageable);
        } else if (category != null && priority != null) {
            return taskRepository.findByUserAndCategoryAndPriority(user, category, priority, pageable);
        } else if (completed != null) {
            return taskRepository.findByUserAndCompleted(user, completed, pageable);
        } else if (category != null) {
            return taskRepository.findByUserAndCategory(user, category, pageable);
        } else if (priority != null) {
            return taskRepository.findByUserAndPriority(user, priority, pageable);
        } else {
            return taskRepository.findByUser(user, pageable);
        }
    }
    
    public TaskResponse updateTask(Long id, TaskRequest taskRequest, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this task");
        }
        
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate()); // This will now use the ZonedDateTime method
        task.setPriority(taskRequest.getPriority());
        task.setCategory(taskRequest.getCategory());
        
        Task updatedTask = taskRepository.save(task);
        return convertToTaskResponse(updatedTask);
    }
    
    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this task");
        }
        
        taskRepository.delete(task);
    }
    
    public TaskResponse markTaskAsCompleted(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this task");
        }
        
        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        return convertToTaskResponse(updatedTask);
    }
    
    public TaskResponse markTaskAsPending(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this task");
        }
        
        task.setCompleted(false);
        Task updatedTask = taskRepository.save(task);
        return convertToTaskResponse(updatedTask);
    }
    
    private TaskResponse convertToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCompleted(),
                task.getPriority(),
                task.getCategory(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate(),
                task.getUser().getId()
        );
    }
}