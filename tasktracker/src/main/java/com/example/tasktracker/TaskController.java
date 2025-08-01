package com.example.tasktracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return taskRepository.findById(id)
            .map(task -> ResponseEntity.ok(task))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return repository.save(task);
    }

    // Update a task (mark as completed)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (optionalTask.isPresent()) {
        Task existingTask = optionalTask.get();
        existingTask.setTitle(updatedTask.getTitle()); // Set the new title
        taskRepository.save(existingTask);
        return ResponseEntity.ok(existingTask);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    if (taskRepository.existsById(id)) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }

    
}
    
}