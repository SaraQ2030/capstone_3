package com.example.capstone2.Repositry;

import com.example.capstone2.Model.Invoice;
import com.example.capstone2.Model.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRespositry extends JpaRepository<Invoice,Integer> {
    @Query("select i from Invoice i where i.id=?1")
    Invoice findByID(Integer id);
    List<Invoice> findAllByStatus(String status);
    Invoice findByStatus(String status);
    Invoice findByLeaseId(Integer id);

    List<Invoice> findInvoiceByIssueDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, String status);

    //List<Invoice> findInvoiceByIssueDateBetweenAAndStatus(LocalDate startDate,  LocalDate endDate,  String status);
    @Query("select i from  Invoice i where i.status=?1 and i.status=?2")
    List<Invoice> findUnpaidInvoicesByPropertyIdAndStatus(Integer proID, String status);
}
