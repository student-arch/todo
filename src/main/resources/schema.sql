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
    priority VARCHAR(20) DEFAULT 'MEDIUM',
    category VARCHAR(20) DEFAULT 'OTHER',
    user_id BIGINT NOT NULL,
    reminder_enabled BOOLEAN DEFAULT FALSE,
    reminder_time TIMESTAMP NULL,
    is_recurring BOOLEAN DEFAULT FALSE,
    recurring_pattern VARCHAR(20) NULL, -- 'DAILY', 'WEEKLY', 'MONTHLY'
    recurring_end_date TIMESTAMP NULL,
    position_order INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_tasks_user_id ON tasks(user_id);
CREATE INDEX IF NOT EXISTS idx_tasks_completed ON tasks(is_completed);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date ON tasks(due_date);
CREATE INDEX IF NOT EXISTS idx_tasks_priority ON tasks(priority);
CREATE INDEX IF NOT EXISTS idx_tasks_category ON tasks(category);
CREATE INDEX IF NOT EXISTS idx_tasks_reminder_time ON tasks(reminder_time);
CREATE INDEX IF NOT EXISTS idx_tasks_recurring ON tasks(is_recurring);
CREATE INDEX IF NOT EXISTS idx_tasks_position ON tasks(position_order);

-- Create user preferences table for theme settings
CREATE TABLE IF NOT EXISTS user_preferences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    theme VARCHAR(20) DEFAULT 'LIGHT', -- 'LIGHT', 'DARK'
    notifications_enabled BOOLEAN DEFAULT TRUE,
    reminder_before_minutes INT DEFAULT 30, -- Minutes before due date to send reminder
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (user_id)
);