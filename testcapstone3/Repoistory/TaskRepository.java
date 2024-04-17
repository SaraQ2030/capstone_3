package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    Task findTaskBy
}
