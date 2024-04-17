package com.example.testcapstone3.Controller;
import com.example.testcapstone3.ApiResponse.APIResponse;
import com.example.testcapstone3.DTO.AppealDTO;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Service.CasseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/case")

public class CasseController {

    Logger logger =LoggerFactory.getLogger(CasseController.class);
    private final CasseService caseService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        logger.info("inside get-all");
        return ResponseEntity.status(HttpStatus.OK).body(caseService.getall());
    }
    @PostMapping("/add/{clientID}")
    public ResponseEntity add(@PathVariable Integer clientID,@RequestBody @Valid Casse casse){
        logger.info("inside add");

        caseService.add(clientID,casse);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Case is added successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody@Valid Casse casse,@PathVariable Integer id){
        logger.info("inside update");

        caseService.upDate(casse,id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Case is updated successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        logger.info("inside delete");

        caseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Case is deleted successfully"));
    }

    ///============================  Gate one Extra 1 =======================
    @PostMapping("appeal-case")
    public ResponseEntity appealCase(@RequestBody @Valid AppealDTO appealDTO){
        caseService.AppeaThecase(appealDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Appeal added successfully"));
    }
//closeCase
//Integer caseID, Integer userID, String result
    @PutMapping("close-case/{caseID}/{userID}")
    public ResponseEntity appealCase(@PathVariable Integer caseID ,@PathVariable Integer userID ,@RequestBody String result){
        caseService.closeCase(caseID,userID,result);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("case closed successfully with result : "+result));
    }
    @PutMapping("start-case/{caseID}/{userID}")
    public ResponseEntity startCase(@PathVariable Integer caseID ,@PathVariable Integer userID ){
        caseService.startCase(caseID,userID);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("case start successfully with status investigation"));
    }

    //public void acceptClientRequest(Integer caseId,Integer lawyerId){
    @PutMapping("accept-case/{caseID}/{userID}")
    public ResponseEntity acceptClientRequest(@PathVariable Integer caseID ,@PathVariable Integer userID ){
        caseService.acceptClientRequest(caseID,userID);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Lawyer accepted case successfully "));
    }
    //startCase

}

