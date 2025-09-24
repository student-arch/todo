package com.javaproject.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaproject.todo.model.TaskCategory;
import com.javaproject.todo.model.TaskPriority;

import java.time.LocalDateTime;

public class TaskResponse {
    
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private TaskPriority priority;
    private TaskCategory category;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
    
    private Long userId;
    
    public TaskResponse() {
    }
    
    public TaskResponse(Long id, String title, String description, Boolean completed, 
                        TaskPriority priority, TaskCategory category, 
                        LocalDateTime createdAt, LocalDateTime updatedAt, 
                        LocalDateTime dueDate, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueDate = dueDate;
        this.userId = userId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    public TaskPriority getPriority() {
        return priority;
    }
    
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    
    public TaskCategory getCategory() {
        return category;
    }
    
    public void setCategory(TaskCategory category) {
        this.category = category;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}