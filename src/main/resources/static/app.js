// TODO App JavaScript Functionality

// API Base URL - change this to match your backend API URL
const API_BASE_URL = 'http://localhost:8080/api';

// DOM Elements
const authSection = document.getElementById('auth-section');
const dashboardSection = document.getElementById('dashboard-section');
const navbar = document.getElementById('navbar');
const loginForm = document.getElementById('login-form');
const signupForm = document.getElementById('signup-form');
const logoutBtn = document.getElementById('logout-btn');
const usernameDisplay = document.getElementById('username');
const taskForm = document.getElementById('task-form');
const taskFormTitle = document.getElementById('task-form-title');
const taskIdInput = document.getElementById('task-id');
const taskTitleInput = document.getElementById('task-title');
const taskDescriptionInput = document.getElementById('task-description');
const taskDueDateInput = document.getElementById('task-due-date');
const taskPrioritySelect = document.getElementById('task-priority');
const taskCategorySelect = document.getElementById('task-category');
const taskStatusSelect = document.getElementById('task-status');
const saveTaskBtn = document.getElementById('save-task-btn');
const cancelTaskBtn = document.getElementById('cancel-task-btn');
const taskListContainer = document.getElementById('task-list-container');
const noTasksMessage = document.getElementById('no-tasks-message');
const taskCount = document.getElementById('task-count');
const searchInput = document.getElementById('search-input');
const searchBtn = document.getElementById('search-btn');
const statusFilter = document.getElementById('status-filter');
const categoryFilter = document.getElementById('category-filter');
const priorityFilter = document.getElementById('priority-filter');
const sortOptions = document.getElementById('sort-options');
const taskTemplate = document.getElementById('task-template');

// State Management
let authToken = localStorage.getItem('authToken') || null;
let currentUser = JSON.parse(localStorage.getItem('currentUser')) || null;
let tasks = [];
let filteredTasks = [];
let editingTaskId = null;

// Initialize App
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

function initializeApp() {
    // Check if user is already logged in
    if (authToken && currentUser) {
        showDashboard();
        fetchTasks();
    } else {
        showAuthSection();
    }
    
    // Event Listeners
    loginForm.addEventListener('submit', handleLogin);
    signupForm.addEventListener('submit', handleSignup);
    logoutBtn.addEventListener('click', handleLogout);
    taskForm.addEventListener('submit', handleTaskSubmit);
    cancelTaskBtn.addEventListener('click', resetTaskForm);
    searchBtn.addEventListener('click', handleSearch);
    searchInput.addEventListener('keyup', function(e) {
        if (e.key === 'Enter') {
            handleSearch();
        }
    });
    statusFilter.addEventListener('change', applyFilters);
    categoryFilter.addEventListener('change', applyFilters);
    priorityFilter.addEventListener('change', applyFilters);
    sortOptions.addEventListener('change', applyFilters);
    
    // Mobile responsiveness
    handleMobileResponsiveness();
    window.addEventListener('resize', handleMobileResponsiveness);
}

// Authentication Functions
async function handleLogin(e) {
    e.preventDefault();
    
    const username = document.getElementById('login-username').value.trim();
    const password = document.getElementById('login-password').value.trim();
    
    // Form validation
    if (!username || !password) {
        showAlert('Please fill in all fields', 'danger');
        return;
    }
    
    showLoading(true);
    
    try {
        const response = await fetch(`${API_BASE_URL}/auth/signin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            // Store authentication data
            authToken = data.token;
            currentUser = {
                id: data.id,
                username: data.username,
                email: data.email
            };
            
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            showAlert('Login successful!', 'success');
            showDashboard();
            fetchTasks();
        } else {
            showAlert(data.message || 'Login failed', 'danger');
        }
    } catch (error) {
        console.error('Login error:', error);
        showAlert('Network error. Please try again later.', 'danger');
    } finally {
        showLoading(false);
    }
}

async function handleSignup(e) {
    e.preventDefault();
    
    const username = document.getElementById('signup-username').value.trim();
    const email = document.getElementById('signup-email').value.trim();
    const password = document.getElementById('signup-password').value.trim();
    
    // Form validation
    if (!username || !email || !password) {
        showAlert('Please fill in all fields', 'danger');
        return;
    }
    
    if (password.length < 6) {
        showAlert('Password must be at least 6 characters', 'danger');
        return;
    }
    
    showLoading(true);
    
    try {
        const response = await fetch(`${API_BASE_URL}/auth/signup`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, email, password })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            showAlert('Registration successful! Please login.', 'success');
            // Switch to login tab
            document.getElementById('login-tab').click();
            // Reset signup form
            signupForm.reset();
        } else {
            showAlert(data.message || 'Registration failed', 'danger');
        }
    } catch (error) {
        console.error('Signup error:', error);
        showAlert('Network error. Please try again later.', 'danger');
    } finally {
        showLoading(false);
    }
}

function handleLogout() {
    // Clear authentication data
    authToken = null;
    currentUser = null;
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    
    // Reset forms
    loginForm.reset();
    signupForm.reset();
    
    // Show auth section
    showAuthSection();
    
    showAlert('You have been logged out', 'info');
}

// UI Functions
function showAuthSection() {
    authSection.style.display = 'block';
    dashboardSection.style.display = 'none';
    navbar.style.display = 'none';
}

function showDashboard() {
    authSection.style.display = 'none';
    dashboardSection.style.display = 'block';
    navbar.style.display = 'block';
    usernameDisplay.textContent = currentUser.username;
}

function showLoading(show) {
    // Create or remove loading indicator
    let loadingIndicator = document.getElementById('loading-indicator');
    
    if (show) {
        if (!loadingIndicator) {
            loadingIndicator = document.createElement('div');
            loadingIndicator.id = 'loading-indicator';
            loadingIndicator.className = 'loader';
            document.body.appendChild(loadingIndicator);
        }
    } else {
        if (loadingIndicator) {
            loadingIndicator.remove();
        }
    }
}

function showAlert(message, type) {
    // Create alert element
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    alertDiv.style.top = '20px';
    alertDiv.style.right = '20px';
    alertDiv.style.zIndex = '9999';
    alertDiv.style.minWidth = '300px';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    document.body.appendChild(alertDiv);
    
    // Auto dismiss after 5 seconds
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

// Task Management Functions
async function fetchTasks() {
    if (!authToken) return;
    
    showLoading(true);
    
    try {
        console.log('DEBUG: Fetching tasks with auth token:', authToken ? 'Present' : 'Missing');
        if (authToken) {
            console.log('DEBUG: Auth token first 20 chars:', authToken.substring(0, 20));
        }
        const response = await fetch(`${API_BASE_URL}/tasks`, {
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        console.log('DEBUG: Fetch tasks response status:', response.status);
        
        if (response.ok) {
            tasks = await response.json();
            filteredTasks = [...tasks];
            renderTasks();
            updateTaskCount();
        } else {
            if (response.status === 401) {
                // Token expired or invalid
                handleLogout();
                showAlert('Session expired. Please login again.', 'warning');
            } else {
                showAlert('Failed to fetch tasks', 'danger');
            }
        }
    } catch (error) {
        console.error('Fetch tasks error:', error);
        showAlert('Network error. Please try again later.', 'danger');
    } finally {
        showLoading(false);
    }
}

function renderTasks() {
    // Clear existing tasks
    while (taskListContainer.firstChild) {
        taskListContainer.removeChild(taskListContainer.firstChild);
    }
    
    if (filteredTasks.length === 0) {
        // Show no tasks message
        const noTasksDiv = document.createElement('div');
        noTasksDiv.className = 'col-12 text-center py-5';
        noTasksDiv.id = 'no-tasks-message';
        noTasksDiv.innerHTML = `
            <i class="bi bi-inbox fs-1 text-muted"></i>
            <p class="text-muted mt-2">No tasks found. Add a new task to get started!</p>
        `;
        taskListContainer.appendChild(noTasksDiv);
        return;
    }
    
    // Render each task
    filteredTasks.forEach(task => {
        const taskElement = createTaskElement(task);
        taskListContainer.appendChild(taskElement);
    });
}

function createTaskElement(task) {
    // Clone the task template
    const taskElement = document.importNode(taskTemplate.content, true);
    
    // Set task data
    const taskCard = taskElement.querySelector('.task-card');
    const taskTitle = taskElement.querySelector('.task-title');
    const taskDescription = taskElement.querySelector('.task-description');
    const taskCategory = taskElement.querySelector('.task-category');
    const taskPriority = taskElement.querySelector('.task-priority');
    const taskDueDate = taskElement.querySelector('.task-due-date');
    const taskStatusCheckbox = taskElement.querySelector('.task-status-checkbox');
    const taskStatusLabel = taskElement.querySelector('.task-status-label');
    const editTaskBtn = taskElement.querySelector('.edit-task-btn');
    const deleteTaskBtn = taskElement.querySelector('.delete-task-btn');
    const toggleStatusBtn = taskElement.querySelector('.toggle-status-btn');
    
    // Set values
    taskTitle.textContent = task.title;
    taskDescription.textContent = task.description || 'No description';
    taskCategory.textContent = task.category;
    taskPriority.textContent = task.priority;
    taskDueDate.textContent = formatDate(task.dueDate);
    taskStatusLabel.textContent = task.completed ? 'Completed' : 'Pending';
    
    // Apply styling based on task properties
    applyTaskStyling(taskCard, task.priority.toLowerCase(), task.category.toLowerCase(), task.completed);
    
    // Add event listeners
    editTaskBtn.addEventListener('click', () => editTask(task));
    deleteTaskBtn.addEventListener('click', () => deleteTask(task.id));
    toggleStatusBtn.addEventListener('click', () => toggleTaskStatus(task.id, task.completed));
    taskStatusCheckbox.addEventListener('change', () => toggleTaskStatus(task.id, task.completed));
    
    return taskElement;
}

function applyTaskStyling(taskElement, priority, category, status) {
    // Apply priority styling
    taskElement.classList.remove('priority-high', 'priority-medium', 'priority-low');
    taskElement.classList.add(`priority-${priority}`);
    taskElement.setAttribute('data-priority', priority);
    
    // Apply category styling
    const categoryElement = taskElement.querySelector('.task-category');
    categoryElement.setAttribute('data-category', category);
    
    // Apply status styling
    if (status) {
        taskElement.classList.add('completed-task');
        taskElement.querySelector('.task-status-checkbox').checked = true;
    } else {
        taskElement.classList.remove('completed-task');
        taskElement.querySelector('.task-status-checkbox').checked = false;
    }
}

function formatDate(dateString) {
    if (!dateString) return 'No due date';
    
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

function updateTaskCount() {
    const count = filteredTasks.length;
    taskCount.textContent = `${count} task${count !== 1 ? 's' : ''}`;
}

async function handleTaskSubmit(e) {
    e.preventDefault();
    
    const title = taskTitleInput.value.trim();
    const description = taskDescriptionInput.value.trim();
    const dueDate = taskDueDateInput.value;
    const priority = taskPrioritySelect.value;
    const category = taskCategorySelect.value;
    const status = taskStatusSelect.value === 'completed';
    
    // Form validation
    if (!title) {
        showAlert('Title is required', 'danger');
        return;
    }
    
    if (!dueDate) {
        showAlert('Due date is required', 'danger');
        return;
    }
    
    showLoading(true);
    
    // Create task request object
    const dueDateObj = new Date(dueDate);
    const dueDateIso = dueDateObj.toISOString();
    console.log('DEBUG: Original due date:', dueDate);
    console.log('DEBUG: Due date object:', dueDateObj);
    console.log('DEBUG: Due date ISO string:', dueDateIso);
    
    const taskRequest = {
        title,
        description,
        dueDate: dueDateIso,
        priority: priority.toUpperCase(),
        category: category.toUpperCase()
    };
    
    console.log('DEBUG: Task request object:', taskRequest);
    
    try {
        let response;
        
        if (editingTaskId) {
            // Update existing task
            response = await fetch(`${API_BASE_URL}/tasks/${editingTaskId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                },
                body: JSON.stringify(taskRequest)
            });
        } else {
            // Create new task
            response = await fetch(`${API_BASE_URL}/tasks`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                },
                body: JSON.stringify(taskRequest)
            });
        }
        
        const data = await response.json();
        
        if (response.ok) {
            showAlert(editingTaskId ? 'Task updated successfully!' : 'Task created successfully!', 'success');
            resetTaskForm();
            fetchTasks();
        } else {
            showAlert(data.message || 'Failed to save task', 'danger');
        }
    } catch (error) {
        console.error('Save task error:', error);
        showAlert('Network error. Please try again later.', 'danger');
    } finally {
        showLoading(false);
    }
}

function editTask(task) {
    // Populate form with task data
    taskIdInput.value = task.id;
    taskTitleInput.value = task.title;
    taskDescriptionInput.value = task.description || '';
    taskDueDateInput.value = formatDateForInput(task.dueDate);
    taskPrioritySelect.value = task.priority.toLowerCase();
    taskCategorySelect.value = task.category.toLowerCase();
    taskStatusSelect.value = task.completed ? 'completed' : 'pending';
    
    // Update form title and button
    taskFormTitle.textContent = 'Edit Task';
    saveTaskBtn.textContent = 'Update Task';
    
    // Set editing task ID
    editingTaskId = task.id;
    
    // Scroll to form
    document.getElementById('task-form-section').scrollIntoView({ behavior: 'smooth' });
}

function formatDateForInput(dateString) {
    if (!dateString) return '';
    
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
}

function resetTaskForm() {
    taskForm.reset();
    taskIdInput.value = '';
    taskFormTitle.textContent = 'Add New Task';
    saveTaskBtn.textContent = 'Save Task';
    editingTaskId = null;
}

async function deleteTask(taskId) {
    if (!confirm('Are you sure you want to delete this task?')) {
        return;
    }
    
    showLoading(true);
    
    try {
        const response = await fetch(`${API_BASE_URL}/tasks/${taskId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        
        const data = await response.json();
        
        if (response.ok) {
            showAlert('Task deleted successfully!', 'success');
            fetchTasks();
        } else {
            showAlert(data.message || 'Failed to delete task', 'danger');
        }
    } catch (error) {
        console.error('Delete task error:', error);
        showAlert('Network error. Please try again later.', 'danger');
    } finally {
        showLoading(false);
    }
}

async function toggleTaskStatus(taskId, currentStatus) {
    showLoading(true);
    
    try {
        const endpoint = currentStatus ? 'pending' : 'complete';
        const response = await fetch(`${API_BASE_URL}/tasks/${taskId}/${endpoint}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        
        const data = await response.json();
        
        if (response.ok) {
            showAlert(`Task marked as ${currentStatus ? 'pending' : 'completed'}!`, 'success');
            fetchTasks();
        } else {
            showAlert(data.message || 'Failed to update task status', 'danger');
        }
    } catch (error) {
        console.error('Toggle task status error:', error);
        showAlert('Network error. Please try again later.', 'danger');
    } finally {
        showLoading(false);
    }
}

// Search and Filter Functions
function handleSearch() {
    applyFilters();
}

function applyFilters() {
    const searchTerm = searchInput.value.trim().toLowerCase();
    const statusValue = statusFilter.value;
    const categoryValue = categoryFilter.value;
    const priorityValue = priorityFilter.value;
    const sortBy = sortOptions.value;
    
    // Apply filters
    filteredTasks = tasks.filter(task => {
        // Search filter
        if (searchTerm && !task.title.toLowerCase().includes(searchTerm) && 
            !(task.description && task.description.toLowerCase().includes(searchTerm))) {
            return false;
        }
        
        // Status filter
        if (statusValue !== 'all') {
            if (statusValue === 'completed' && !task.completed) return false;
            if (statusValue === 'pending' && task.completed) return false;
        }
        
        // Category filter
        if (categoryValue !== 'all' && task.category.toLowerCase() !== categoryValue) {
            return false;
        }
        
        // Priority filter
        if (priorityValue !== 'all' && task.priority.toLowerCase() !== priorityValue) {
            return false;
        }
        
        return true;
    });
    
    // Apply sorting
    filteredTasks.sort((a, b) => {
        let valueA, valueB;
        
        switch (sortBy) {
            case 'dueDate':
                valueA = new Date(a.dueDate || '9999-12-31');
                valueB = new Date(b.dueDate || '9999-12-31');
                break;
            case 'createdDate':
                valueA = new Date(a.createdAt);
                valueB = new Date(b.createdAt);
                break;
            case 'priority':
                const priorityOrder = { 'high': 3, 'medium': 2, 'low': 1, 'urgent': 4 };
                valueA = priorityOrder[a.priority.toLowerCase()] || 0;
                valueB = priorityOrder[b.priority.toLowerCase()] || 0;
                break;
            default:
                return 0;
        }
        
        // Sort in descending order
        if (valueA > valueB) return -1;
        if (valueA < valueB) return 1;
        return 0;
    });
    
    renderTasks();
    updateTaskCount();
}

// Mobile Responsiveness
function handleMobileResponsiveness() {
    // This function is now primarily handled by CSS Grid
    // We'll keep it for any future JavaScript-based responsive adjustments
    const container = document.getElementById('task-list-container');
    if (container) {
        // Force a reflow to ensure the grid updates properly
        container.style.display = 'none';
        container.offsetHeight; // Trigger reflow
        container.style.display = 'grid';
    }
}