package com.javaproject.todo.controller;

import com.javaproject.todo.dto.MessageResponse;
import com.javaproject.todo.dto.TaskRequest;
import com.javaproject.todo.dto.TaskResponse;
import com.javaproject.todo.model.TaskCategory;
import com.javaproject.todo.model.TaskPriority;
import com.javaproject.todo.model.User;
import com.javaproject.todo.repository.UserRepository;
import com.javaproject.todo.security.UserPrinciple;
import com.javaproject.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest taskRequest,
                                       @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            System.out.println("DEBUG: Creating task with dueDate: " + taskRequest.getDueDate());
            System.out.println("DEBUG: User principle: " + (userPrinciple != null ? userPrinciple.getUsername() : "null"));
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            TaskResponse taskResponse = taskService.createTask(taskRequest, user);
            return ResponseEntity.ok(taskResponse);
        } catch (Exception e) {
            System.out.println("DEBUG: Error creating task: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTaskById(@PathVariable Long id,
                                        @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            TaskResponse taskResponse = taskService.getTaskById(id, user);
            return ResponseEntity.ok(taskResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllTasksForUser(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            List<TaskResponse> tasks = taskService.getAllTasksForUser(user);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/filtered")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTasksWithFiltersAndSorting(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) TaskCategory category,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @AuthenticationPrincipal UserPrinciple userPrinciple) {
        
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Page<TaskResponse> tasks = taskService.getTasksWithFiltersAndSorting(
                    user, completed, category, priority, keyword, page, size, sortBy, sortDir);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @Valid @RequestBody TaskRequest taskRequest,
                                        @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            TaskResponse taskResponse = taskService.updateTask(id, taskRequest, user);
            return ResponseEntity.ok(taskResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable Long id,
                                        @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            taskService.deleteTask(id, user);
            return ResponseEntity.ok(new MessageResponse("Task deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> markTaskAsCompleted(@PathVariable Long id,
                                                @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            TaskResponse taskResponse = taskService.markTaskAsCompleted(id, user);
            return ResponseEntity.ok(taskResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/pending")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> markTaskAsPending(@PathVariable Long id,
                                              @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            TaskResponse taskResponse = taskService.markTaskAsPending(id, user);
            return ResponseEntity.ok(taskResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}