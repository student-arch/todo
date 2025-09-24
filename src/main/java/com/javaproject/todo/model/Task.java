package com.javaproject.todo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 500)
    private String description;
    
    @Column(name = "is_completed")
    private Boolean completed;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private TaskCategory category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "reminder_enabled")
    private Boolean reminderEnabled;
    
    @Column(name = "reminder_time")
    private LocalDateTime reminderTime;
    
    @Column(name = "is_recurring")
    private Boolean isRecurring;
    
    @Column(name = "recurring_pattern")
    private String recurringPattern;
    
    @Column(name = "recurring_end_date")
    private LocalDateTime recurringEndDate;
    
    @Column(name = "position_order")
    private Integer positionOrder;
    
    public Task() {
        this.completed = false;
        this.priority = TaskPriority.MEDIUM;
        this.category = TaskCategory.OTHER;
        this.reminderEnabled = false;
        this.isRecurring = false;
        this.positionOrder = 0;
    }
    
    public Task(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.completed = false;
        this.priority = TaskPriority.MEDIUM;
        this.category = TaskCategory.OTHER;
        this.reminderEnabled = false;
        this.isRecurring = false;
        this.positionOrder = 0;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    
    // Helper method to accept ZonedDateTime and convert to LocalDateTime
    public void setDueDate(ZonedDateTime zonedDueDate) {
        if (zonedDueDate != null) {
            this.dueDate = zonedDueDate.toLocalDateTime();
        } else {
            this.dueDate = null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    // Helper method to accept ZonedDateTime and convert to LocalDateTime for reminder time
    public void setReminderTime(ZonedDateTime zonedReminderTime) {
        if (zonedReminderTime != null) {
            this.reminderTime = zonedReminderTime.toLocalDateTime();
        } else {
            this.reminderTime = null;
        }
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

    public LocalDateTime getRecurringEndDate() {
        return recurringEndDate;
    }

    public void setRecurringEndDate(LocalDateTime recurringEndDate) {
        this.recurringEndDate = recurringEndDate;
    }

    // Helper method to accept ZonedDateTime and convert to LocalDateTime for recurring end date
    public void setRecurringEndDate(ZonedDateTime zonedRecurringEndDate) {
        if (zonedRecurringEndDate != null) {
            this.recurringEndDate = zonedRecurringEndDate.toLocalDateTime();
        } else {
            this.recurringEndDate = null;
        }
    }

    public Integer getPositionOrder() {
        return positionOrder;
    }

    public void setPositionOrder(Integer positionOrder) {
        this.positionOrder = positionOrder;
    }
}