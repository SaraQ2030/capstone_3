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

    //18
    @PostMapping("/appeal-case")
    public ResponseEntity appealCase(@RequestBody @Valid AppealDTO appealDTO){
        caseService.AppeaThecase(appealDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Appeal requested successfully"));
    }
    //17
//closeCase
//Integer caseID, Integer userID, String result
    @PutMapping("close-case/{caseID}/{userID}/{result}")
    public ResponseEntity closeCase(@PathVariable Integer caseID ,@PathVariable Integer userID ,@PathVariable String result){
        caseService.closeCase(caseID,userID,result);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("case closed successfully with result : "+result));
    }

    //16
    @PutMapping("start-case/{caseID}/{userID}")
    public ResponseEntity startCase(@PathVariable Integer caseID ,@PathVariable Integer userID ){
        caseService.startCase(caseID,userID);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("case start successfully with status investigation"));
    }
//15
    //public void acceptClientRequest(Integer caseId,Integer lawyerId){
    @PutMapping("accept-case/{caseID}/{userID}")
    public ResponseEntity acceptClientRequest(@PathVariable Integer caseID ,@PathVariable Integer userID ){
        caseService.acceptClientRequest(caseID,userID);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Lawyer accepted case successfully "));
    }
    //startCase



    //-------------------------------------------------------------------------------
    //14
//get all cases for specific client

    @GetMapping("client-cases/{clientId}")
    public ResponseEntity listCasesByClient(@PathVariable Integer clientId  ){
        logger.info("inside get all Cases By Client");
        return ResponseEntity.status(200).body(caseService.getCasesByClientId(clientId));
    }
    //-------------------------------------------------------------------------------
    //13
//get specific case for specific client
    @GetMapping("get-case-client/{clientId}/{caseId}")
    public ResponseEntity searchCaseByClient(@PathVariable Integer clientId ,@PathVariable Integer caseId ){
        logger.info("inside get one Case By Client id");
        return ResponseEntity.status(200).body(caseService.searchCaseByClientId(clientId,caseId));
    }

    @GetMapping("change-status-lawsuit/{caseId}")
    public ResponseEntity changeStatuslawsuit(@PathVariable Integer caseId ){
        logger.info("inside change Status");
        caseService.changeStatus(caseId);
        return ResponseEntity.ok().body(new APIResponse("change status lawsuit successfully"));
    }

    @GetMapping("get-one/{caseId}")
    public ResponseEntity getOneCase(@PathVariable Integer caseId ){
        logger.info("inside get one case");
        return ResponseEntity.ok().body(  caseService.getOneCasse(caseId));
    }

    //getCasesByTypeOfLawsuits

//
    @GetMapping("get-cases-by-type-of-lawsuits/{TypeOfLawsuits}")
    public ResponseEntity getCasesByTypeOfLawsuits(@PathVariable String TypeOfLawsuits ){
        logger.info("inside get one case");
        return ResponseEntity.ok().body(  caseService.getCasesByTypeOfLawsuits(TypeOfLawsuits));
    }
}

