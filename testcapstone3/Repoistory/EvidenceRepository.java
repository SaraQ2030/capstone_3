package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence,Integer> {

    Evidence findEvidenceById(Integer id);
}
