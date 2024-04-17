package com.example.testcapstone3.Service;

import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Task;
import com.example.testcapstone3.Repoistory.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public void updateTask(Integer id, Task task) {
        Task t = taskRepository.findTaskById(id);
        if (t == null) {
            throw new APIException("task not found!");
        }
        t.setStatus(task.getStatus());
        t.setCost(task.getCost());
        t.setLawyer_id(task.getLawyer_id());
        t.setDescription(task.getDescription());

        taskRepository.save(t);
    }

    public void deleteTask(Integer id) {
        Task task = taskRepository.findTaskById(id);
        if (task == null) {
            throw new APIException("task not found!");
        }
        taskRepository.delete(task);
    }


//    //method to get tasks by status!
//    public List<Task> getTasksByStatus(String status) {
//        return taskRepository.findTasksByStatus(status);
//    }
//
//    //method to get tasks by user!
//    public List<Task> getTasksByUser(Integer userId) {
//        return taskRepository.findTasksByUserId(userId);
//    }
//
//    //method to get task by id!
//    public Task getTaskById(Integer id) {
//        Task task = taskRepository.findTaskByUserId(id);
//
//        if (task == null) {
//            throw new APIException("Task not found!");
//        }
//        return task;
//    }



}