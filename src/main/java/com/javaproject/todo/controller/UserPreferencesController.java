package com.javaproject.todo.controller;

import com.javaproject.todo.dto.UserPreferencesRequest;
import com.javaproject.todo.dto.UserPreferencesResponse;
import com.javaproject.todo.model.User;
import com.javaproject.todo.repository.UserRepository;
import com.javaproject.todo.security.UserPrinciple;
import com.javaproject.todo.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/preferences")
public class UserPreferencesController {
    
    @Autowired
    private UserPreferencesService userPreferencesService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserPreferences(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            UserPreferencesResponse userPreferencesResponse = userPreferencesService.getUserPreferences(user);
            return ResponseEntity.ok(userPreferencesResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new com.javaproject.todo.dto.MessageResponse(e.getMessage()));
        }
    }
    
    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUserPreferences(
            @Valid @RequestBody UserPreferencesRequest userPreferencesRequest,
            @AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            User user = userRepository.findById(userPrinciple.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            UserPreferencesResponse userPreferencesResponse = userPreferencesService.updateUserPreferences(userPreferencesRequest, user);
            return ResponseEntity.ok(userPreferencesResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new com.javaproject.todo.dto.MessageResponse(e.getMessage()));
        }
    }
}