package com.example.capstone2.Controller;

import com.example.capstone2.ApiResponse.APIResponse;
import com.example.capstone2.Model.Tenant;
import com.example.capstone2.Service.TenantService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RestController@RequiredArgsConstructor
@RequestMapping("/api/v1/tenant")
public class TenantController {


    private final TenantService tenantService;
    Logger logger= LoggerFactory.getLogger(TenantController.class);

//    @Autowired
//    private RestTemplate restTemplate;


    @GetMapping("/get-all-tenants")
    public List<Tenant> getAllTenants() {
        logger.info("inside get-all-tenants");
        return tenantService.getAllTenants();
    }

    @GetMapping("get-by/{id}")
    public ResponseEntity getTenantById(@PathVariable Integer id) {
        logger.info("inside getTenantById");
        return ResponseEntity.status(HttpStatus.OK).body(tenantService.getTenantById(id));
    }

    @PostMapping("/add-tenant")
    public ResponseEntity createTenant(@RequestBody @Valid Tenant tenant) {
        logger.info("inside createTenant");
        tenantService.addTenant(tenant);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Tenant added successfully"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateTenant(@PathVariable Integer id, @RequestBody @Valid Tenant tenant) {
        logger.info("inside updateTenant");
        tenantService.updateTenant(id, tenant);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Tenant updated successfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteTenant(@PathVariable Integer id) {
        logger.info("inside deleteTenant");
        tenantService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Tenant deleted successfully"));
    }
    //Integer tenantID, Integer propertyId
@PutMapping("rent-property/{tenantID}/{propertyId}")
    public ResponseEntity rentProperty(@PathVariable Integer tenantID, @PathVariable Integer propertyId){
        logger.info("inside rent-property");
        tenantService.rentProperty(tenantID,propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Successfully rent you can see your lease"));
    }

    //---------------------------------------  5 extra endpoints ------------------------
    //1
    @PostMapping("send-maintenance-request/{tenantID}/{propertyID}")
    public ResponseEntity sendMaintenanceRequest(@PathVariable Integer tenantID, @PathVariable Integer propertyID, @RequestBody String description) {
        logger.info("inside send-maintenance-request");

        tenantService.sendMAinTenanceRequest(tenantID, propertyID, description);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Send Maintenance request successfully"));
    }

    //2
    @PostMapping("re-send-request/{tenantID}/{mainRequestID}")
    public ResponseEntity reSendRequest(@PathVariable Integer tenantID, @PathVariable Integer mainRequestID) {
        logger.info("inside re-send-request");
        tenantService.reSendRequest(tenantID,mainRequestID);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Re-Send Maintenance request successfully with ID " + mainRequestID));
    }

    //3
    @GetMapping("find-tenants-expired-date")
    public ResponseEntity findTenantsExpiredDate() {
        logger.info("inside find-tenants-expired-date");
        return ResponseEntity.status(HttpStatus.OK).body(tenantService.findTenantsExpiredDate());
    }

    //4

    //5

//
//    //calculateTotalRentdebt
//    //findTenantsExpiredDate(LocalDate startDate, LocalDate endDate) {
//    //6
    @GetMapping("find-tenants-with-expired-lease")
    public ResponseEntity findTenantsWithExpiredLease() {
        logger.info("inside find Tenants With Expired Lease");
        return ResponseEntity.status(HttpStatus.OK).body(tenantService.findTenantsWithExpiredLease());
    }
//
//    //7
    //findTenantsWithMultipleLeases
    @GetMapping("find-tenants-with-multiple-leases")
    public ResponseEntity findTenantsWithmultipleLeases() {
        logger.info("inside find Tenants With multiple Leases");

        return ResponseEntity.status(HttpStatus.OK).body(tenantService.findTenantsWithmultipleLeases());

    }



}