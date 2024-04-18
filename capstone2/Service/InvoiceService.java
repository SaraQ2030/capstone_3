package com.example.capstone2.Service;

import com.example.capstone2.ApiResponse.APIException;
import com.example.capstone2.Model.*;
import com.example.capstone2.Repositry.InvoiceRespositry;
import com.example.capstone2.Repositry.LeaseRepositry;
import com.example.capstone2.Repositry.PropertyRepositry;
import com.example.capstone2.Repositry.TenantRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRespositry invoiceRespositry;
    private final TenantRepositry tenantRepositry;
    private final LeaseRepositry leaseRepositry;
    private final PropertyRepositry propertyRepositry;

    public List<Invoice> getall() {

        if (invoiceRespositry.findAll().isEmpty()) {
            throw new APIException("EmptyList");
        }
        return invoiceRespositry.findAll();
    }

    public void add(Invoice invoice) {
        if (leaseRepositry.findByID(invoice.getLeaseId()) != null) {
            if (tenantRepositry.findByID(invoice.getTenantId()) != null) {
                invoiceRespositry.save(invoice);

            } else {
                throw new APIException("Tenant ID not found");
            }
        } else {
            throw new APIException("Lease ID not found");
        }
    }

    public void update(Integer id, Invoice invoice) {
        if (invoiceRespositry.findByID(id) == null) {
            throw new APIException("Invoice ID not found");
        }
        if (leaseRepositry.findByID(invoice.getLeaseId()) != null) {
            throw new APIException("Lease ID not found");
        }
        if (tenantRepositry.findByID(invoice.getTenantId()) != null) {
            throw new APIException("Tenant ID not found");
        }
        Invoice retriveinvoice = invoiceRespositry.findByID(id);
        retriveinvoice.setLeaseId(invoice.getLeaseId());
        retriveinvoice.setPrice(invoice.getPrice());
        retriveinvoice.setTenantId(invoice.getTenantId());
        retriveinvoice.setIssueDate(invoice.getIssueDate());
    }


//-----------------------------------------------------------------

    public void delete(Integer id) {
        if (invoiceRespositry.findByID(id) == null) {
            throw new APIException("Invoice ID not found" + id);
        } else invoiceRespositry.delete(invoiceRespositry.findByID(id));

    }

    public List<Invoice> getStatus(String status) {
        if (invoiceRespositry.findAllByStatus(status).isEmpty())
            throw new APIException("No invoice with status" + status);

        else return invoiceRespositry.findAllByStatus(status);
    }

    public Invoice getOne(Integer id) {
        if (invoiceRespositry.findByID(id) == null) {
            throw new APIException("no invoice with id" + id);
        }
        return invoiceRespositry.findByID(id);
    }

    public void createNew(Integer tenantID, Integer leaseID) {
        if (tenantRepositry.findTenantById(tenantID) == null) {
            throw new APIException("Tenant with ID " + tenantID + " not found");
        }
        if (leaseRepositry.findByID(leaseID) == null) {
            throw new APIException("lease with ID " + leaseID + " not found");
        } else {
            Invoice invoice = new Invoice();
            invoice.setTenantId(tenantID);
            invoice.setLeaseId(leaseID);
            invoice.setPrice(leaseRepositry.findByID(leaseID).getRentAmount());
            invoice.setIssueDate(LocalDate.now());
            invoice.setStatus("NOT_PAID");
            invoiceRespositry.save(invoice);
        }

    }

    // DONE             DONE            DONE DONE             DONE            DONE
    public List<Tenant> overdue() {
        List<Tenant> tenants = new ArrayList<>();
        for (Lease lease : leaseRepositry.findAll()) {
            Invoice invoice = invoiceRespositry.findByLeaseId(lease.getId());
            if (invoice != null && LocalDate.now().isAfter(lease.getEndDate().plusMonths(1)) &&
                    invoiceRespositry.findByLeaseId(lease.getId()).getStatus().equalsIgnoreCase("NOT_PAID")) {
                invoiceRespositry.findByLeaseId(lease.getId()).setStatus("OVERDUE");
                tenants.add(tenantRepositry.findByID(lease.getTenantId()));

            }
        }
        if (tenants.isEmpty()) throw new APIException("No Tenants with overdue");
        return tenants;
    }

    ///--------------------
    public Map<String, String> calculateTotalRentdebt(String status) {

        Map<String, Double> totalRentDebtByType = new HashMap<>();
        Map<String, Double> totalRentByType = new HashMap<>();
        Map<String, Integer> propertyCountByType = new HashMap<>();
        Map<String, String> rentDebtFormatted = new HashMap<>();

        List<Property> properties = propertyRepositry.findAll();
        for (Property property : properties) {
            String type = property.getType();
            double rentPrice = property.getRentPrice();
            if (!totalRentByType.containsKey(type)) {
                totalRentByType.put(type, 0.0);
                propertyCountByType.put(type, 0);
            }
            totalRentByType.put(type, totalRentByType.get(type) + rentPrice);
            propertyCountByType.put(type, propertyCountByType.get(type) + 1);
        }

        for (String type : totalRentByType.keySet()) {
            double totalRentDebt = 0.0;

            List<Property> propertiesOfType = propertyRepositry.findPropertyByType(type);
            for (Property property : propertiesOfType) {
                Integer propertyId = property.getId();

                List<Invoice> unpaidInvoices = invoiceRespositry.findAllByStatus(status);
                for (Invoice invoice : unpaidInvoices) {
                    totalRentDebt += invoice.getPrice();
                }
            }

            totalRentDebtByType.put(type, totalRentDebt);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        for (String type : totalRentDebtByType.keySet()) {
            double totalRentDebt = totalRentDebtByType.get(type);
            rentDebtFormatted.put(type, df.format(totalRentDebt));
        }

        return rentDebtFormatted;
    }

    // DONE         DONE    DONE // DONE         DONE    DONE // DONE         DONE    DONE

    public void changeStatusPAY(Integer id) {
        if (invoiceRespositry.findByID(id) == null) {
            throw new APIException("Invoice with ID " + id + " not found");
        }
        Invoice invoice = invoiceRespositry.findByID(id);
        if (invoice.getStatus().equalsIgnoreCase("NOT_PAID")) {
            invoice.setStatus("PAID");
            invoiceRespositry.save(invoice);
        }//start status

        else if (invoice.getStatus().equalsIgnoreCase("OVERDUE")) {
            invoice.setStatus("PAID");
            invoiceRespositry.save(invoice);

        } else if (invoice.getStatus().equalsIgnoreCase("PARTIALLY_PAID")) {
            invoice.setStatus("PAID");
            invoiceRespositry.save(invoice);

        } else {
            throw new APIException("Invoice with ID " + id + " already complete PAID");
        }
    }
// DONE         DONE            DONE        DONE

    public void changePARTIALLY_PAID(Integer id) {
        if (invoiceRespositry.findByID(id) == null) {
            throw new APIException("Invoice with ID " + id + " not found");
        }
        Invoice invoice = invoiceRespositry.findByID(id);
        if (invoice.getStatus().equalsIgnoreCase("NOT_PAID")) {
            invoice.setStatus("PARTIALLY_PAID");
            invoiceRespositry.save(invoice);
        }//start status

        else if (invoice.getStatus().equalsIgnoreCase("OVERDUE")) {
            invoice.setStatus("PARTIALLY_PAID");
            invoiceRespositry.save(invoice);

        } else {
            throw new APIException("Invoice with ID " + id + " already complete PAID");
        }
    }


    // ------------------------- 1 -------------------------
// DONE         DONE            DONE        DONE
    public double calculateTotalRevenueByStatus(LocalDate startDate, LocalDate endDate, String status) {
        double totalRevenue = 0;
        for (Invoice invoice : invoiceRespositry.findInvoiceByIssueDateBetweenAndStatus(startDate, endDate, status)) {
            totalRevenue += invoice.getPrice();
        }
        return totalRevenue;
    }



   

}

