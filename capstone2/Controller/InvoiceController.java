package com.example.capstone2.Controller;

import com.example.capstone2.ApiResponse.APIResponse;
import com.example.capstone2.Model.Invoice;
import com.example.capstone2.Service.InvoiceService;
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
@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {
    
    private final InvoiceService invoiceService;
    Logger logger= LoggerFactory.getLogger(InvoiceController.class);
    @GetMapping("get-all-invoice")
    public List<Invoice> getAll() {

        logger.info("inside get-all-invoice");
    return invoiceService.getall();
    }



    @PostMapping("/add-invoice")
    public ResponseEntity add(@RequestBody @Valid Invoice invoice) {
        logger.info("inside add-invoice");
        invoiceService.add(invoice);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Invoice add successfully"));
    }

    @PutMapping("update-invoice-/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody @Valid Invoice invoice) {
        logger.info("inside update-invoice by id");

        invoiceService.update(id,invoice);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Invoice updated successfully"));
    }

    @DeleteMapping("delete-invoice/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        logger.info("inside delete-invoice by id");
        invoiceService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Invoice deleted successfully"));
    }
@GetMapping("get-status/{status}")
    public ResponseEntity getStatus(@PathVariable String status){
    logger.info("inside get-status-invoice by {status}");

    return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getStatus(status));
}
    @GetMapping("get-invoice/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        logger.info("inside get-invoice by {status}");
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getOne(id));

    }

    @GetMapping("get-overdue")
    public ResponseEntity overdue() {
        logger.info("inside get-overdue");
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.overdue());
    }

    @GetMapping("/get-calculateTotal-rent-debt/{status}")
    public ResponseEntity debt(@PathVariable String status) {
        logger.info("inside get-calculateTotal-rent-debt/{status}");
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.calculateTotalRentdebt(status));
    }
    @PutMapping("change-status-invoice/{id}")
    public ResponseEntity changeStatusPAY(@PathVariable Integer id){
        logger.info("inside change-status-invoice/{id}");
        invoiceService.changeStatusPAY(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("change successfully"));
    }

    @PutMapping("change-status-invoice-partial/{id}")
    public ResponseEntity changeStatusPARTIAL(@PathVariable Integer id){
        logger.info("inside change-status-invoice-partial/{id}");
        invoiceService.changePARTIALLY_PAID(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("change successfully"));
    }

    //calculateTotalRevenueByStatus
    //changeStatusRequest
    //1
@GetMapping("calculate-total-revenue/{startDate}/{endDate}/{status}")
private ResponseEntity calculateTotalRevenueByStatus(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate ,@PathVariable String status){
    logger.info("inside calculate-total-revenue/{startDate}/{endDate}/{status}");
    return ResponseEntity.status(HttpStatus.OK).body(invoiceService.calculateTotalRevenueByStatus(startDate,endDate,status));
}
}
