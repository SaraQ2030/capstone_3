package com.example.testcapstone3.Controller;

import com.example.testcapstone3.ApiResponse.APIResponse;
import com.example.testcapstone3.Model.Evidence;
import com.example.testcapstone3.Service.EvidenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController@RequestMapping("api/v1/evidence")
public class EvidenceController {

    Logger logger = LoggerFactory.getLogger(EvidenceController.class);
    private final EvidenceService evidenceService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        logger.info("inside get-all");
        return ResponseEntity.status(HttpStatus.OK).body(evidenceService.getAll());
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Evidence evidence){
        logger.info("inside add");

        evidenceService.add(evidence);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("evidence case is added successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody@Valid Evidence evidence ,@PathVariable Integer id){
        logger.info("inside update");

        evidenceService.update(evidence,id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("evidence is updated successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        logger.info("inside delete");

        evidenceService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("evidence is deleted successfully"));
    }



}

