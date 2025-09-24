package com.javaproject.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class UserPreferencesResponse {
    
    private Long id;
    private String theme;
    private Boolean notificationsEnabled;
    private Integer reminderBeforeMinutes;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private Long userId;
    
    public UserPreferencesResponse() {
    }
    
    public UserPreferencesResponse(Long id, String theme, Boolean notificationsEnabled, 
                                  Integer reminderBeforeMinutes, LocalDateTime createdAt, 
                                  LocalDateTime updatedAt, Long userId) {
        this.id = id;
        this.theme = theme;
        this.notificationsEnabled = notificationsEnabled;
        this.reminderBeforeMinutes = reminderBeforeMinutes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public Integer getReminderBeforeMinutes() {
        return reminderBeforeMinutes;
    }

    public void setReminderBeforeMinutes(Integer reminderBeforeMinutes) {
        this.reminderBeforeMinutes = reminderBeforeMinutes;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}