package com.example.testcapstone3.Controller;

import com.example.testcapstone3.ApiResponse.APIResponse;
import com.example.testcapstone3.Model.Task;
import com.example.testcapstone3.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {
    Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @GetMapping("/get")
    public ResponseEntity getAllTasks(){
        logger.info("inside get all tasks!");
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @PostMapping("/add")
    public ResponseEntity addTask(@RequestBody @Valid Task task){
        logger.info("inside add task!");
        taskService.addTask(task);
        return ResponseEntity.ok().body(new APIResponse("task added successfully!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@PathVariable Integer id, @RequestBody @Valid Task task){
        logger.info("inside update task!");
        taskService.updateTask(id, task);
        return ResponseEntity.ok().body(new APIResponse("task updated successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Integer id){
        logger.info("inside delete task!");
        taskService.deleteTask(id);
        return ResponseEntity.ok().body(new APIResponse("task deleted successfully!"));
    }
}
