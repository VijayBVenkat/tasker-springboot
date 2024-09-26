package com.vijay.tasker.controller;

import com.vijay.tasker.DTO.TaskDTO;
import com.vijay.tasker.model.Task;
import com.vijay.tasker.model.User;
import com.vijay.tasker.service.TaskService;
import com.vijay.tasker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Task createTask(@RequestBody TaskDTO taskDTO) {

        User user = userService.findByUsername(taskDTO.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCreatedAt(taskDTO.getCreatedAt());
        task.setUser(user);

//        return taskService.createTask(task);
        return taskService.createTaskAndLogOperation(task, taskDTO.getEmail());
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }
}
