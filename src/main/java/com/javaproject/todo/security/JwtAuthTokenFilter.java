package com.javaproject.todo.security;

import com.javaproject.todo.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            logger.debug("Processing request for URI: {}", request.getRequestURI());
            logger.debug("JWT Token: {}", jwt != null ? "Present" : "Missing");
            
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                logger.debug("Username from JWT: {}", username);
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authentication set for user: {}", username);
            } else {
                logger.debug("No valid JWT token found for URI: {}", request.getRequestURI());
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        
        return null;
    }
}