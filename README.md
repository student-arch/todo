# TODO Application

A full-stack TODO application built with Spring Boot for the backend and HTML/CSS/JavaScript for the frontend. This application allows users to create, manage, and organize their tasks with features like user authentication, task categorization, priority levels, and search/filter functionality.

## Features

- **User Authentication**: Secure signup and login functionality with JWT tokens
- **Task Management**: Create, read, update, and delete tasks
- **Task Organization**: 
  - Categories (Work, Personal, Study, Other, etc.)
  - Priority levels (High, Medium, Low, Urgent)
  - Status tracking (Pending, Completed)
- **Search and Filter**: Search tasks by keywords and filter by category, priority, or status
- **Responsive Design**: Works seamlessly on mobile, tablet, and desktop devices
- **RESTful API**: Well-structured backend API with proper error handling

## Technology Stack

### Backend
- **Spring Boot 3.5.6** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access
- **JWT (JSON Web Token)** - Token-based authentication
- **H2 Database** - In-memory database for testing
- **Maven** - Build and dependency management

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling with responsive design
- **JavaScript (ES6+)** - Interactive functionality
- **Bootstrap 5.3.0** - UI framework
- **Bootstrap Icons** - Icon library

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Modern web browser (Chrome, Firefox, Safari, Edge)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd todo
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Database Configuration

The application uses an H2 in-memory database for testing and development. The database configuration is located in `src/main/resources/application.properties`:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:todo_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=root
spring.datasource.password=nullbyte
spring.datasource.driver-class-name=org.h2.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

### H2 Console

You can access the H2 database console at `http://localhost:8080/h2-console` when the application is running.

- **JDBC URL**: `jdbc:h2:mem:todo_db`
- **User Name**: `root`
- **Password**: `nullbyte`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/signin` - User login

### Tasks
- `GET /api/tasks` - Get all tasks for the authenticated user
- `GET /api/tasks/{id}` - Get a specific task by ID
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update an existing task
- `DELETE /api/tasks/{id}` - Delete a task
- `PUT /api/tasks/{id}/complete` - Mark a task as completed
- `PUT /api/tasks/{id}/pending` - Mark a task as pending
- `GET /api/tasks/filtered` - Get tasks with filters and sorting

### Filtering and Sorting
The filtered endpoint supports the following parameters:
- `completed` (boolean): Filter by completion status
- `category` (string): Filter by category
- `priority` (string): Filter by priority
- `keyword` (string): Search in title and description
- `page` (int): Page number (default: 0)
- `size` (int): Page size (default: 10)
- `sortBy` (string): Sort field (default: createdAt)
- `sortDir` (string): Sort direction (default: desc)

## How to Use the Application

### 1. User Registration
1. Open the application in your browser
2. Click on the "Sign Up" tab
3. Enter your username, email, and password
4. Click "Sign Up"

### 2. Login
1. After registration, switch to the "Login" tab
2. Enter your username and password
3. Click "Login"

### 3. Managing Tasks
- **Create a Task**: Click on the "Add Task" link in the navigation, fill in the task details, and click "Save Task"
- **Edit a Task**: Click the three dots menu on a task card and select "Edit"
- **Delete a Task**: Click the three dots menu on a task card and select "Delete"
- **Mark as Complete**: Use the checkbox in the task card footer or select "Toggle Status" from the menu
- **Filter Tasks**: Use the search and filter controls in the dashboard section

### 4. Search and Filter
- **Search**: Enter keywords in the search box and click the search button
- **Filter by Status**: Select "All", "Pending", or "Completed" from the status dropdown
- **Filter by Category**: Select a category from the category dropdown
- **Filter by Priority**: Select a priority level from the priority dropdown
- **Sort**: Choose to sort by due date, created date, or priority

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/javaproject/todo/
│   │       ├── controller/     # REST controllers
│   │       ├── dto/           # Data transfer objects
│   │       ├── model/         # Entity classes
│   │       ├── repository/    # JPA repositories
│   │       ├── security/      # Security configuration
│   │       ├── service/       # Business logic
│   │       └── TodoApplication.java  # Main application class
│   └── resources/
│       ├── application.properties  # Application configuration
│       ├── schema.sql            # Database schema
│       └── static/               # Frontend assets
│           ├── index.html         # Main HTML file
│           ├── styles.css         # CSS styles
│           └── app.js             # JavaScript code
└── test/
    └── java/
        └── com/javaproject/todo/
            └── TodoApplicationTests.java  # Test classes
```

## Security Features

- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **Password Encryption**: Passwords are encrypted using BCrypt
- **Role-Based Access**: Users have roles (USER, ADMIN) for authorization
- **CORS Configuration**: Cross-Origin Resource Sharing properly configured
- **Input Validation**: Form inputs validated both on client and server side

## Responsive Design

The application is fully responsive and works on:
- **Mobile devices** (320px - 576px)
- **Tablet devices** (577px - 992px)
- **Desktop devices** (993px and above)

Key responsive features:
- Adaptive grid layout for task cards
- Responsive navigation with collapsible menu
- Touch-friendly buttons and controls
- Optimized form layouts for different screen sizes

## Testing

The application includes comprehensive testing of:
- User authentication (signup/signin)
- Task CRUD operations
- Search and filter functionality
- Responsive design on different screen sizes
- Database connectivity
- CORS configuration

## Future Enhancements

Potential future improvements:
- Email notifications for task reminders
- Task sharing between users
- File attachments for tasks
- Recurring tasks
- Advanced analytics and reporting
- Integration with calendar applications
- Dark mode theme

## License

This project is open source and available under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a pull request.

## Support

If you encounter any issues or have questions, please open an issue in the repository.