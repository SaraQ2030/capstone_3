package com.example.testcapstone3.Service;
import com.example.testcapstone3.DTO.DocumentDTO;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.Document;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.ClientRepository;
import com.example.testcapstone3.Repoistory.DocumentRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentationRepository;
    private final CasseRepository casseRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

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


    //------------------------------EXTRA---------------------------------------------------
    //extra 15
    //user send request to client to upload document        Sarah
    public void clientUploadDocument(DocumentDTO documentDTO,Integer clientID,Integer lawyerID){
        Casse casse=casseRepository.findCasseById(documentDTO.getCase_id());
        Client client=clientRepository.findClientById(clientID);
        User user=userRepository.findUserById(lawyerID);

// Check if the case, client, and lawyer exist
        if (casse==null){
            throw new APIException("Case Not found");
        } if (client==null){
            throw new APIException("Client Not found");
        } if (user==null){
            throw new APIException("lawyer Not found");
        }
        //to check if the client have this case with this lawyer
        if (!(casse.getClients().getId()==clientID  && casse.getUsser().getId()== lawyerID )){
            throw new APIException("the client ,case and lawyer don't match");
        }
        // If everything is ok, proceed to add document
        if (casse.getStatus().equalsIgnoreCase("taken")){

        }

        // Add document using the DocumentDTO object
        addDocument(documentDTO);
    }

}