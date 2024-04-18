package com.example.capstone2.Controller;

import com.example.capstone2.ApiResponse.APIResponse;
import com.example.capstone2.Model.Property;
import com.example.capstone2.Model.Tenant;
import com.example.capstone2.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;
    Logger logger= LoggerFactory.getLogger(PropertyController.class);

    @GetMapping("get-all-property")
    public List<Property> getAllProperty() {
        logger.info("inside get-all-property");
        return propertyService.getAll();
    }

    @GetMapping("get-property/{id}")
    public ResponseEntity getPropertyById(@PathVariable Integer id) {
        logger.info("inside get-property by id");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getByID(id));
    }

    @PostMapping("/add-property")
    public ResponseEntity addProperty(@RequestBody @Valid Property property) {
        logger.info("inside add-property");
        propertyService.add(property);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Property added successfully"));
    }

    @PutMapping("update-property/{id}")
    public ResponseEntity updateProperty(@PathVariable Integer id, @RequestBody @Valid Property property) {
        logger.info("inside update-property");
        propertyService.update(id,property);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Property updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProperty(@PathVariable Integer id) {
        logger.info("inside delete-property");
        propertyService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Property deleted successfully"));
    }

    //--------------------------- 6 extra endpoints -------------------------
    //1
    @GetMapping("get-calculate-average-rent-price")
    public ResponseEntity calculateAverageRentPrice(){
        logger.info("get-calculate-average-rent-price");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.calculateAverageRentPrice());
    }
    //2
    @GetMapping("get-average-rent-for-each-type")
    public ResponseEntity AverageRenforechaType(){
        logger.info("get-average-rent-for-each-type");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.AverageRenforechaType());
    }
    //3
    @GetMapping("find-free-property")
    public ResponseEntity findfreeProperties(){
        logger.info("find-free-property");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findfreeProperties());
    }
    //4
    @GetMapping("get-pending-requests")
    public ResponseEntity getPendingRequests(){
        logger.info("get-pending-requests");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getPendingRequests());
    }
    //4
    @GetMapping("get-short-leases")
    public ResponseEntity getshhortLeases(){
        logger.info("get-short-leases");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getshhortLeases());
    }
    //5
    @GetMapping("get-total-revenue-each-property/{startDate}/{endDate}")
    public ResponseEntity calculateTotalRevenueByProperty(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        logger.info("get-total-revenue-each-property/{startDate}/{endDate}");
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.calculateTotalRevenueByProperty(startDate,endDate));
    }


}
