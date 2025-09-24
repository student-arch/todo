package com.javaproject.todo.service;

import com.javaproject.todo.dto.UserPreferencesRequest;
import com.javaproject.todo.dto.UserPreferencesResponse;
import com.javaproject.todo.model.User;
import com.javaproject.todo.model.UserPreferences;
import com.javaproject.todo.repository.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferencesService {
    
    @Autowired
    private UserPreferencesRepository userPreferencesRepository;
    
    public UserPreferencesResponse getUserPreferences(User user) {
        UserPreferences userPreferences = userPreferencesRepository.findByUser(user)
                .orElseGet(() -> {
                    UserPreferences newPreferences = new UserPreferences(user);
                    return userPreferencesRepository.save(newPreferences);
                });
        
        return convertToUserPreferencesResponse(userPreferences);
    }
    
    public UserPreferencesResponse updateUserPreferences(UserPreferencesRequest userPreferencesRequest, User user) {
        UserPreferences userPreferences = userPreferencesRepository.findByUser(user)
                .orElseGet(() -> new UserPreferences(user));
        
        userPreferences.setTheme(userPreferencesRequest.getTheme());
        userPreferences.setNotificationsEnabled(userPreferencesRequest.getNotificationsEnabled());
        userPreferences.setReminderBeforeMinutes(userPreferencesRequest.getReminderBeforeMinutes());
        
        UserPreferences updatedPreferences = userPreferencesRepository.save(userPreferences);
        return convertToUserPreferencesResponse(updatedPreferences);
    }
    
    private UserPreferencesResponse convertToUserPreferencesResponse(UserPreferences userPreferences) {
        return new UserPreferencesResponse(
                userPreferences.getId(),
                userPreferences.getTheme(),
                userPreferences.getNotificationsEnabled(),
                userPreferences.getReminderBeforeMinutes(),
                userPreferences.getCreatedAt(),
                userPreferences.getUpdatedAt(),
                userPreferences.getUser().getId()
        );
    }
}