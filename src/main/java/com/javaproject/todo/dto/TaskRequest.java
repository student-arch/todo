package com.javaproject.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaproject.todo.model.TaskCategory;
import com.javaproject.todo.model.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public class TaskRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime dueDate;
    
    @NotNull(message = "Priority is required")
    private TaskPriority priority;
    
    @NotNull(message = "Category is required")
    private TaskCategory category;
    
    private Boolean reminderEnabled;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime reminderTime;
    
    private Boolean isRecurring;
    
    private String recurringPattern; // 'DAILY', 'WEEKLY', 'MONTHLY'
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime recurringEndDate;
    
    private Integer positionOrder;
    
    public TaskRequest() {
    }
    
    public TaskRequest(String title, String description, ZonedDateTime dueDate, TaskPriority priority, TaskCategory category) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
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
    
    public ZonedDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
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

    public Boolean getReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(Boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }

    public ZonedDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(ZonedDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public Boolean getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public String getRecurringPattern() {
        return recurringPattern;
    }

    public void setRecurringPattern(String recurringPattern) {
        this.recurringPattern = recurringPattern;
    }

    public ZonedDateTime getRecurringEndDate() {
        return recurringEndDate;
    }

    public void setRecurringEndDate(ZonedDateTime recurringEndDate) {
        this.recurringEndDate = recurringEndDate;
    }

    public Integer getPositionOrder() {
        return positionOrder;
    }

    public void setPositionOrder(Integer positionOrder) {
        this.positionOrder = positionOrder;
    }
}