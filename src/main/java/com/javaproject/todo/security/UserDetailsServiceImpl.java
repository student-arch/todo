package com.javaproject.todo.security;

import com.javaproject.todo.model.User;
import com.javaproject.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DEBUG: Looking up user by username: '" + username + "'");
        System.out.println("DEBUG: Username length: " + username.length());
        System.out.println("DEBUG: Username hashcode: " + username.hashCode());
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("DEBUG: User not found in database for username: '" + username + "'");
                    return new UsernameNotFoundException("User Not Found with username: " + username);
                });
        
        System.out.println("DEBUG: User found in database:");
        System.out.println("DEBUG: User ID: " + user.getId());
        System.out.println("DEBUG: User username: '" + user.getUsername() + "'");
        System.out.println("DEBUG: User email: " + user.getEmail());
        System.out.println("DEBUG: User username length: " + user.getUsername().length());
        System.out.println("DEBUG: User username hashcode: " + user.getUsername().hashCode());
        
        return UserPrinciple.build(user);
    }
}