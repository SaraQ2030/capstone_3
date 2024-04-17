package com.example.testcapstone3.Service;
import com.example.testcapstone3.DTO.DocumentDTO;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Document;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.DocumentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentationRepository;
    private final CasseRepository casseRepository;

    public List<Document> getAllDocuments(){
        return documentationRepository.findAll();
    }
    public void addDocument(DocumentDTO documentDTO){
        Casse cas =casseRepository.findCasseById(documentDTO.getCase_id());
        if (cas==null){
            throw new APIException("Not found case to assign document to ");
        }
        Document  doc=new Document(null, documentDTO.getTitle(), documentDTO.getDescription(), documentDTO.getFilePath(),cas);
        documentationRepository.save(doc);
    }
    public void updateDocument(DocumentDTO documentDTO){
        Document doc=documentationRepository.findDocumentById(documentDTO.getCase_id());
        if (doc==null){
            throw new APIException("Not found document");
        }
        doc.setDescription(documentDTO.getDescription());
        doc.setTitle(documentDTO.getTitle());
        doc.setFilePath(documentDTO.getFilePath());
        documentationRepository.save(doc);
    }

    public void deleteDocument(Integer id){
        Document doc=documentationRepository.findDocumentById(id);
        if (doc==null){
            throw new APIException("Not found document");
        }
        documentationRepository.delete(doc);
    }

}