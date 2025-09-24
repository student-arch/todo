package com.javaproject.todo.repository;

import com.javaproject.todo.model.Task;
import com.javaproject.todo.model.TaskCategory;
import com.javaproject.todo.model.TaskPriority;
import com.javaproject.todo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByUser(User user);
    
    List<Task> findByUserAndCompleted(User user, Boolean completed);
    
    List<Task> findByUserAndCategory(User user, TaskCategory category);
    
    List<Task> findByUserAndPriority(User user, TaskPriority priority);
    
    List<Task> findByUserAndCategoryAndPriority(User user, TaskCategory category, TaskPriority priority);
    
    List<Task> findByUserAndCompletedAndCategory(User user, Boolean completed, TaskCategory category);
    
    List<Task> findByUserAndCompletedAndPriority(User user, Boolean completed, TaskPriority priority);
    
    List<Task> findByUserAndCompletedAndCategoryAndPriority(User user, Boolean completed, TaskCategory category, TaskPriority priority);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndKeyword(@Param("user") User user, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndCompletedAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.category = :category AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndCategoryAndKeyword(@Param("user") User user, @Param("category") TaskCategory category, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndPriorityAndKeyword(@Param("user") User user, @Param("priority") TaskPriority priority, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND t.category = :category AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndCompletedAndCategoryAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("category") TaskCategory category, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndCompletedAndPriorityAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("priority") TaskPriority priority, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.category = :category AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndCategoryAndPriorityAndKeyword(@Param("user") User user, @Param("category") TaskCategory category, @Param("priority") TaskPriority priority, @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND t.category = :category AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Task> findByUserAndCompletedAndCategoryAndPriorityAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("category") TaskCategory category, @Param("priority") TaskPriority priority, @Param("keyword") String keyword);
    
    Page<Task> findByUser(User user, Pageable pageable);
    
    Page<Task> findByUserAndCompleted(User user, Boolean completed, Pageable pageable);
    
    Page<Task> findByUserAndCategory(User user, TaskCategory category, Pageable pageable);
    
    Page<Task> findByUserAndPriority(User user, TaskPriority priority, Pageable pageable);
    
    Page<Task> findByUserAndCategoryAndPriority(User user, TaskCategory category, TaskPriority priority, Pageable pageable);
    
    Page<Task> findByUserAndCompletedAndCategory(User user, Boolean completed, TaskCategory category, Pageable pageable);
    
    Page<Task> findByUserAndCompletedAndPriority(User user, Boolean completed, TaskPriority priority, Pageable pageable);
    
    Page<Task> findByUserAndCompletedAndCategoryAndPriority(User user, Boolean completed, TaskCategory category, TaskPriority priority, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndKeyword(@Param("user") User user, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndCompletedAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.category = :category AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndCategoryAndKeyword(@Param("user") User user, @Param("category") TaskCategory category, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndPriorityAndKeyword(@Param("user") User user, @Param("priority") TaskPriority priority, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND t.category = :category AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndCompletedAndCategoryAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("category") TaskCategory category, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndCompletedAndPriorityAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("priority") TaskPriority priority, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.category = :category AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndCategoryAndPriorityAndKeyword(@Param("user") User user, @Param("category") TaskCategory category, @Param("priority") TaskPriority priority, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = :completed AND t.category = :category AND t.priority = :priority AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> findByUserAndCompletedAndCategoryAndPriorityAndKeyword(@Param("user") User user, @Param("completed") Boolean completed, @Param("category") TaskCategory category, @Param("priority") TaskPriority priority, @Param("keyword") String keyword, Pageable pageable);
}