package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    Client findClientById(Integer id);


}
