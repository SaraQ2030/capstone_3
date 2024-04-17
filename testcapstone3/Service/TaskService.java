package com.example.testcapstone3.Service;

import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Task;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.TaskRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void addTask(Integer user_id, Task task) {
        User user = userRepository.findUserById(user_id);
        if (user == null) {
            throw new APIException("can't assign, user id not found!");
        }
        task.setUsser(user);
        taskRepository.save(task);
    }

    public void updateTask(Integer id, Task task) {
        Task t = taskRepository.findTaskById(id);
        if (t == null) {
            throw new APIException("task not found!");
        }
        t.setStatus(task.getStatus());
        t.setCost(task.getCost());
        t.setUsser(task.getUsser());
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


    //method to get tasks by status!
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findTasksByStatus(status);
    }

    //Method to get Tasks by Priority
    public List<Task> getTasksByPriority(String priority) {
        return taskRepository.findTasksByPriority(priority);
    }
}