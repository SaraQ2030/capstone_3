package com.example.capstone2.Service;

import com.example.capstone2.ApiResponse.APIException;
import com.example.capstone2.Model.Invoice;
import com.example.capstone2.Model.Lease;
import com.example.capstone2.Model.Property;
import com.example.capstone2.Model.Tenant;
import com.example.capstone2.Repositry.InvoiceRespositry;
import com.example.capstone2.Repositry.LeaseRepositry;
import com.example.capstone2.Repositry.PropertyRepositry;
import com.example.capstone2.Repositry.TenantRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaseService {
    private final LeaseRepositry leaseRepositry;
    private final PropertyRepositry propertyRepositry;
    private final InvoiceService invoiceService;
    private final TenantRepositry tenantRepositry;

    public List<Lease> getall() {
        if (leaseRepositry.findAll().isEmpty())
            throw new APIException(" lease Empty list ");
        return leaseRepositry.findAll();
    }

    public void add(Lease lease) {

        leaseRepositry.save(lease); //each lease connect with invoice automated
        Invoice invoice = new Invoice();
        invoice.setIssueDate(LocalDate.now());
        invoice.setPrice(propertyRepositry.findPropertyById(lease.getPropertyId()).getRentPrice());
        invoice.setTenantId(lease.getTenantId());
        invoice.setLeaseId(lease.getId());
        invoice.setStatus("NOT_PAID");
        invoiceService.add(invoice);
    }


    public void update(Integer id, Lease lease) {
        if (leaseRepositry.findByID(id) != null) {
            if (leaseRepositry.findByID(id).getTenantId() == lease.getTenantId()) {
                if (leaseRepositry.findByID(id).getPropertyId() == lease.getPropertyId()) {
                    Lease retrive = leaseRepositry.findByID(id);
                    retrive.setTenantId(lease.getTenantId());
                    retrive.setEndDate(lease.getEndDate());
                    Tenant tenant = tenantRepositry.findByID(lease.getTenantId());
                    tenant.setMoveInDate(lease.getStartDate());
                    tenant.setMoveOutDate(lease.getEndDate());
                    tenantRepositry.save(tenant);
                    retrive.setStartDate(lease.getStartDate());
                    retrive.setRentAmount(lease.getRentAmount());
                    retrive.setPropertyId(lease.getPropertyId());
                    leaseRepositry.save(retrive);
                } else {
                    throw new APIException("Property ID " + lease.getPropertyId() + " not found");
                }
            } else {
                throw new APIException("Tenant ID " + lease.getTenantId() + " not found");
            }
        } else {
            throw new APIException("Lease ID " + id + " not found");
        }
    }

    public void delete(Integer id) {
        if (leaseRepositry.findByID(id) != null) {
            leaseRepositry.delete(leaseRepositry.findByID(id));
        } else {
            throw new APIException("Lease ID " + id + " not found");
        }


    }

    public Lease getByID(Integer id) {
        if (leaseRepositry.findByID(id) != null) {
            return leaseRepositry.findByID(id);
        } else {
            throw new APIException("Lease ID " + id + " not found");
        }

    }

    //---------------------------- 1 ------------------------

    public Lease findLeasesByTenantId(Integer tenantId) {
        if (leaseRepositry.findLeasesByTenantId(tenantId) == null) {
            throw new APIException("Not found");
        }
        return leaseRepositry.findLeasesByTenantId(tenantId);

    }

    //---------------------- 3 ------------------------
    public List<Lease> expiredLeasesList() {
        List<Lease> expiredLeasesList = new ArrayList<>();
        for (Lease lease : leaseRepositry.findAll()) {
            if (lease.getEndDate().isBefore(LocalDate.now().plusMonths(1))) {
                expiredLeasesList.add(lease);
            }
        }
        if (expiredLeasesList.isEmpty()) {
            throw new APIException("Empty expiredLeases list");
        }
        return expiredLeasesList;
    }

    //------------------------ 4 ---------------------
    public void expiredLeases() {
        for (Tenant tenant : tenantRepositry.findAll()) {
            List<Lease> lease = leaseRepositry.findAllByTenantId(tenant.getId());
            for (Lease l : lease) {
                if (lease != null && l.getEndDate() != null && l.getEndDate().isBefore(LocalDate.now().plusMonths(1)) && tenant.getMoveOutDate() == null) {
                    tenant.setMoveOutDate(LocalDate.now());
                    tenantRepositry.save(tenant);


                }
            }
        }
    }
}
