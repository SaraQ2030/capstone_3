package com.example.testcapstone3.Repoistory;
import com.example.testcapstone3.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByUsserId(Integer id);

    Task findTaskById(Integer id);

    List<Task> findTasksByUsserIdAndStatus(Integer userId, String status);

    List<Task> findTasksByStatus(String status);
    List<Task> findTasksByUsserId(Integer userId);
    Task findTaskByUsserId(Integer id);

    List<Task> findTasksByPriority(String priority);
}