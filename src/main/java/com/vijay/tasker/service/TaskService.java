package com.vijay.tasker.service;

import com.vijay.tasker.model.AuditLog;
import com.vijay.tasker.model.Task;
import com.vijay.tasker.model.User;
import com.vijay.tasker.repository.AuditLogRepository;
import com.vijay.tasker.repository.TaskRepository;
import com.vijay.tasker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Transactional // This ensures that all operations are executed as part of a single transaction.
    public Task createTaskAndLogOperation(Task task, String newEmail) {
        // 1. Save Task for the user
        User user = userRepository.findByUsername(task.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        taskRepository.save(task); // Task is created

        // 2. Update User's email
        user.setEmail(newEmail);
        userRepository.save(user); // Email is updated

        // 3. Log the action in the audit table
        AuditLog auditLog = new AuditLog();
        auditLog.setAction("Task Created for user " + user.getUsername());
        auditLog.setUsername(user.getUsername());
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog); // Audit Log created

        // Simulate a failure in audit logging, causing rollback of all operations
        if (newEmail.equals("fail@example.com")) {
            throw new RuntimeException("Simulating an exception for rollback");
        }

        return task;
    }

}