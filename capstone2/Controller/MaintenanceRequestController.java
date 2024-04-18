package com.example.capstone2.Controller;

import com.example.capstone2.ApiResponse.APIResponse;
import com.example.capstone2.Model.MaintenanceRequest;
import com.example.capstone2.Service.MaintenanceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController@RequiredArgsConstructor
@RequestMapping("/api/v1/maintenance-request")
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;
    Logger logger= LoggerFactory.getLogger(MaintenanceRequestController.class);

    @GetMapping("get-all-maintenance")
    public List<MaintenanceRequest> getAllmaintenance() {
        logger.info("inside get-all-maintenance");
        return maintenanceRequestService.getall();
    }

    @GetMapping("get-maintenance/{id}")
    public ResponseEntity getmaintenanceById(@PathVariable Integer id) {
        logger.info("inside get-maintenance by id");
        return ResponseEntity.status(HttpStatus.OK).body(maintenanceRequestService.getByID(id));
    }

    @PostMapping("/add-maintenance")
    public ResponseEntity addMaintenanceRequest(@RequestBody @Valid MaintenanceRequest maintenanceRequest) {
        logger.info("inside add maintenance");
        maintenanceRequestService.add(maintenanceRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("MaintenanceRequest Sending successfully"));
    }

    @PutMapping("update-maintenance-request/{id}")
    public ResponseEntity updateMaintenanceRequest(@PathVariable Integer id, @RequestBody @Valid MaintenanceRequest maintenanceRequest) {
        logger.info("inside update maintenance");
        maintenanceRequestService.update(id,maintenanceRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("MaintenanceRequest updated successfully"));
    }

    @DeleteMapping("delete-maintenance-request/{id}")
    public ResponseEntity deleteMaintenanceRequest(@PathVariable Integer id) {
        logger.info("inside delete-maintenance");
        maintenanceRequestService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("MaintenanceRequest deleted successfully"));
    }
    //1
    @PutMapping("change-status-request/{id}")
    public ResponseEntity changeStatusRequest(@PathVariable Integer id){
        logger.info("inside change-status-request");
        maintenanceRequestService.changeStatusRequest(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("change successfully"));
    }
    //2

    @GetMapping("give-all-request/{tenantID}/{status}")
    public ResponseEntity giveAllRequest(@PathVariable Integer tenantID,@PathVariable String status){
        logger.info("give-all-request/{tenantID}/{status}");
        return ResponseEntity.status(HttpStatus.OK).body(maintenanceRequestService.giveAllRequest(tenantID,status));
    }





}
