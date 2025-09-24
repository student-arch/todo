package com.javaproject.todo.dto;

import jakarta.validation.constraints.NotNull;

public class UserPreferencesRequest {
    
    @NotNull(message = "Theme is required")
    private String theme;
    
    @NotNull(message = "Notifications enabled is required")
    private Boolean notificationsEnabled;
    
    @NotNull(message = "Reminder before minutes is required")
    private Integer reminderBeforeMinutes;
    
    public UserPreferencesRequest() {
    }
    
    public UserPreferencesRequest(String theme, Boolean notificationsEnabled, Integer reminderBeforeMinutes) {
        this.theme = theme;
        this.notificationsEnabled = notificationsEnabled;
        this.reminderBeforeMinutes = reminderBeforeMinutes;
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
}