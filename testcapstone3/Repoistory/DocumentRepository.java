package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Integer> {

    Document findDocumentById(Integer id);
}
