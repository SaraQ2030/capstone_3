package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Casse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasseRepository extends JpaRepository<Casse,Integer> {

    Casse findCasseById(Integer id);
}
