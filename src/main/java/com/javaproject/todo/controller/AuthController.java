package com.javaproject.todo.controller;

import com.javaproject.todo.dto.JwtResponse;
import com.javaproject.todo.dto.LoginRequest;
import com.javaproject.todo.dto.MessageResponse;
import com.javaproject.todo.dto.SignupRequest;
import com.javaproject.todo.security.JwtUtils;
import com.javaproject.todo.security.UserPrinciple;
import com.javaproject.todo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserService userService;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        
        return ResponseEntity.ok(new JwtResponse(jwt,
                                             userPrincipal.getId(),
                                             userPrincipal.getUsername(),
                                             userPrincipal.getEmail()));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        
        // Create new user
        userService.registerUser(signUpRequest);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}