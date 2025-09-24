-- MySQL Database Setup Script for TODO Application
-- This script creates the database and all required tables for the TODO application

-- Create the database
CREATE DATABASE IF NOT EXISTS todo_db;

-- Use the database
USE todo_db;

-- Create user_roles table to manage many-to-many relationship between users and roles
CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    
    -- Foreign key constraint
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    -- Ensure a user can have only one instance of each role
    UNIQUE KEY unique_user_role (user_id, role)
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create tasks table
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    due_date TIMESTAMP NULL,
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    category ENUM('WORK', 'PERSONAL', 'SHOPPING', 'HEALTH', 'EDUCATION', 'FINANCE', 'HOME', 'OTHER') DEFAULT 'OTHER',
    user_id BIGINT NOT NULL,
    
    -- Foreign key constraint
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_tasks_user_id ON tasks(user_id);
CREATE INDEX IF NOT EXISTS idx_tasks_completed ON tasks(is_completed);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date ON tasks(due_date);
CREATE INDEX IF NOT EXISTS idx_tasks_priority ON tasks(priority);
CREATE INDEX IF NOT EXISTS idx_tasks_category ON tasks(category);
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);

-- Insert default admin user (password: admin123)
-- Note: In a production environment, use a more secure password and consider using a password hash function
INSERT INTO users (username, email, password) VALUES 
('admin', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLkJa8A8sK6dTPjHeOjS')
ON DUPLICATE KEY UPDATE username = username;

-- Insert default user roles for admin
INSERT INTO user_roles (user_id, role) 
SELECT u.id, 'ROLE_ADMIN' FROM users u WHERE u.username = 'admin'
ON DUPLICATE KEY UPDATE role = role;

INSERT INTO user_roles (user_id, role) 
SELECT u.id, 'ROLE_USER' FROM users u WHERE u.username = 'admin'
ON DUPLICATE KEY UPDATE role = role;

-- Insert default regular user for testing (password: user123)
INSERT INTO users (username, email, password) VALUES 
('user', 'user@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi')
ON DUPLICATE KEY UPDATE username = username;

-- Insert default user role for regular user
INSERT INTO user_roles (user_id, role) 
SELECT u.id, 'ROLE_USER' FROM users u WHERE u.username = 'user'
ON DUPLICATE KEY UPDATE role = role;

-- Insert sample tasks for the default user
INSERT INTO tasks (title, description, is_completed, due_date, priority, category, user_id) 
SELECT 
    'Complete project documentation', 
    'Write comprehensive documentation for the TODO application project', 
    FALSE, 
    DATE_ADD(NOW(), INTERVAL 7 DAY), 
    'HIGH', 
    'WORK', 
    u.id 
FROM users u WHERE u.username = 'user'
ON DUPLICATE KEY UPDATE title = title;

INSERT INTO tasks (title, description, is_completed, due_date, priority, category, user_id) 
SELECT 
    'Buy groceries', 
    'Milk, Eggs, Bread, Fruits, and Vegetables', 
    FALSE, 
    DATE_ADD(NOW(), INTERVAL 2 DAY), 
    'MEDIUM', 
    'SHOPPING', 
    u.id 
FROM users u WHERE u.username = 'user'
ON DUPLICATE KEY UPDATE title = title;

INSERT INTO tasks (title, description, is_completed, due_date, priority, category, user_id) 
SELECT 
    'Morning exercise', 
    '30 minutes of cardio and strength training', 
    TRUE, 
    DATE_SUB(NOW(), INTERVAL 1 DAY), 
    'MEDIUM', 
    'HEALTH', 
    u.id 
FROM users u WHERE u.username = 'user'
ON DUPLICATE KEY UPDATE title = title;

-- Grant privileges to the root user
GRANT ALL PRIVILEGES ON todo_db.* TO 'root'@'localhost' IDENTIFIED BY 'nullbyte';
FLUSH PRIVILEGES;

-- Display completion message
SELECT 'Database setup completed successfully!' AS Message;