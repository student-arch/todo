package com.javaproject.todo.service;

import com.javaproject.todo.dto.LoginRequest;
import com.javaproject.todo.dto.SignupRequest;
import com.javaproject.todo.model.Role;
import com.javaproject.todo.model.User;
import com.javaproject.todo.repository.UserRepository;
import com.javaproject.todo.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public User registerUser(SignupRequest signUpRequest) {
        System.out.println("DEBUG: Starting user registration for username: " + signUpRequest.getUsername());
        System.out.println("DEBUG: Email: " + signUpRequest.getEmail());
        
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             passwordEncoder.encode(signUpRequest.getPassword()));
        
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        
        System.out.println("DEBUG: About to save user to database");
        User savedUser = userRepository.save(user);
        System.out.println("DEBUG: User saved with ID: " + savedUser.getId());
        System.out.println("DEBUG: User saved with username: " + savedUser.getUsername());
        System.out.println("DEBUG: User saved with email: " + savedUser.getEmail());
        
        return savedUser;
    }
    
    public UserPrinciple authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (UserPrinciple) authentication.getPrincipal();
    }
    
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}