package com.example.testcapstone3.Controller;
import com.example.testcapstone3.ApiResponse.APIResponse;
import com.example.testcapstone3.DTO.DocumentDTO;
import com.example.testcapstone3.Service.DocumentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/document")
public class DocumentController {
    private final DocumentService documentationService;
    private final Logger logger= LoggerFactory.getLogger(DocumentController.class);
    @GetMapping("/get")
    public ResponseEntity getAllDocument(){
        logger.info("inside get all document");
        return ResponseEntity.status(200).body(documentationService.getAllDocuments());
    }


    @PostMapping("/add")
    public ResponseEntity addDocument(@RequestBody @Valid DocumentDTO documentDTO){
        logger.info("inside add document");
        documentationService.addDocument(documentDTO);
        return ResponseEntity.status(200).body(new APIResponse("Document added"));
    }

    @PutMapping("/update")
    public ResponseEntity updateDocument(@RequestBody @Valid DocumentDTO documentDTO){
        logger.info("inside update document");
        documentationService.updateDocument(documentDTO);
        return ResponseEntity.status(200).body(new APIResponse("Document updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDocument(@PathVariable Integer id){
        logger.info("inside delete document");
        documentationService.deleteDocument(id);
        return ResponseEntity.status(200).body(new APIResponse("Document deleted"));

    }

}
