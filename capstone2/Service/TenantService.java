package com.example.capstone2.Service;

import com.example.capstone2.ApiResponse.APIException;
import com.example.capstone2.Model.*;
import com.example.capstone2.Repositry.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepositry tenantRepositry;
    private final MaintenanceRequestRepositry maintenanceRequestRepositry;
    private final MaintenanceRequestService maintenanceRequestService;
    private final PropertyRepositry propertyRepositry;
    private final LeaseService leaseService;
    private final LeaseRepositry leaseRepositry;
    private final InvoiceRespositry invoiceRespositry;
    @Autowired
    private final InvoiceService invoiceService;
    private static final Logger LOGGER = Logger.getLogger(TenantService.class.getName());

    public List<Tenant> getAllTenants() {
        if (tenantRepositry.findAll().isEmpty())
            throw new APIException("Empty Tenants");
        return tenantRepositry.findAll();
    }

    public Tenant getTenantById(Integer id) {
        if (tenantRepositry.findByID(id) != null)
            return tenantRepositry.findByID(id);
        else {
            throw new APIException("Tenant with ID " + id + " not found");
        }
    }

    public void addTenant(Tenant tenant) {
        tenantRepositry.save(tenant);
    }

    public void updateTenant(Integer id, Tenant tenant) {
        if (tenantRepositry.findByID(id) != null) {
            Tenant retriveTentant = tenantRepositry.findByID(id);
            retriveTentant.setEmail(tenant.getEmail());
            retriveTentant.setAge(tenant.getAge());
            retriveTentant.setName(tenant.getName());
            tenantRepositry.save(retriveTentant);
        } else {
            throw new APIException("Tenant with ID " + id + " not found");
        }

    }

    public void delete(Integer id) {
        if (tenantRepositry.findByID(id) != null) {
            tenantRepositry.deleteById(id);
        } else {
            throw new APIException("Tenant with ID " + id + " not found");
        }
    }

    //---------------------------------  1 ------------------------------------
    //(  tenantID , propertyID ,description)
    public void sendMAinTenanceRequest(Integer tenantID, Integer propertyID, String description) {
        if (tenantRepositry.findByID(tenantID) != null) {
            List<Lease> list = leaseRepositry.findAllByTenantId(tenantID);
            if (list.isEmpty()) {
                throw new APIException("Tenant ID don have lease");
            } else {
                for (Lease lease : list) {

                    if (lease.getPropertyId() == propertyID) {
                        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
                        maintenanceRequest.setTenantId(tenantID);
                        maintenanceRequest.setPropertyId(propertyID);
                        maintenanceRequest.setDescription(description);
                        maintenanceRequestService.add(maintenanceRequest); //injection service
                    } else {
                        throw new APIException("Tenant with ID " + tenantID + " not found");
                    }
                }
            }
        } else {
            throw new APIException("Tenant with ID " + tenantID + " not found");
        }

    }
    //------------------------------------- 2 --------------------------------

    public void reSendRequest(Integer tenantID, Integer mainRequestID) {
        if (tenantRepositry.findByID(tenantID) != null) {
            if (maintenanceRequestRepositry.findMaintenanceRequestByIdAndAndTenantId(mainRequestID, tenantID) != null) {
                MaintenanceRequest maintenanceRequest = maintenanceRequestRepositry.findByID(mainRequestID);
                if (LocalDate.now().isAfter(maintenanceRequest.getDateRequested().plusDays(15))) {
                    MaintenanceRequest resendrequest = maintenanceRequestRepositry.findByID(mainRequestID);
                    resendrequest.setDateRequested(LocalDate.now());//this is updated only
                    maintenanceRequestService.update(mainRequestID, resendrequest);
                } else {
                    throw new APIException("Maintenance Request with ID " + mainRequestID + " Still pending status within period less 15 days");
                }
            } else {
                throw new APIException("Maintenance Request with ID " + mainRequestID + " not found");
            }
        } else {
            throw new APIException("Tenant with ID " + tenantID + " not found");
        }
    }

    //------------------------------------- 3 ---------------------------------
//----------------------------------------  DONE test -------------------------
    public void rentProperty(Integer id, Integer propertyId) {

        if (tenantRepositry.findByID(id) != null && propertyRepositry.findPropertyById(propertyId) != null) {
            if (leaseRepositry.findByPropertyId(propertyId) != null) {
                LOGGER.warning("Property with ID " + propertyId + " Is already Rent");
                throw new APIException("Property with ID " + propertyId + " Is already Rent");
            }
            Lease lease = new Lease();
            lease.setPropertyId(propertyId);
            lease.setStartDate(LocalDate.now());
            Tenant t = tenantRepositry.findTenantById(id);
            t.setMoveInDate(LocalDate.now());
            t.setMoveOutDate(LocalDate.now().plusMonths(1));
            t.setEmail(tenantRepositry.findByID(id).getEmail());
            t.setAge(tenantRepositry.findByID(id).getAge());
            t.setName(tenantRepositry.findByID(id).getName());
            updateTenant(id, t);
            lease.setTenantId(id);
            lease.setEndDate(LocalDate.now().plusMonths(1));
            lease.setRentAmount(propertyRepositry.findPropertyById(propertyId).getRentPrice());
            leaseService.add(lease);//creation automated
            // Lease lease1ID = leaseRepositry.findByPropertyId(propertyId);
        }
        else if (propertyRepositry.findById(propertyId) == null) {
            LOGGER.warning("Property with ID " + propertyId + " not found");

            throw new APIException("Property with ID " + propertyId + " not found");
        }
         else {
            throw new APIException("Tenant ID not found");
        }


    }

//----------------------------------------  DONE test -------------------------


    public List<Tenant> findTenantsExpiredDate() {
        List<Tenant> tenantsWithExpiredLease = new ArrayList<>();
        for (Tenant tenant : tenantRepositry.findAll()) {
            List<Lease> leases = leaseRepositry.findAllByTenantId(tenant.getId());
            for (Lease lease : leases) {
                if (lease.getEndDate().isBefore(LocalDate.now().plusMonths(1))) {
                    tenantsWithExpiredLease.add(tenant);
                }
            }
        }

        return tenantsWithExpiredLease;
    }

    //----------------------------------------  DONE test -------------------------
    public Map<Tenant, Property> findTenantsWithExpiredLease() {
        Map<Tenant, Property> expiredLease = new HashMap<>();

        for (Tenant tenant : tenantRepositry.findAll()) {
            List<Lease> leases = leaseRepositry.findAllByTenantId(tenant.getId());

            for (Lease lease : leases) {
                if (lease.getEndDate().isBefore(LocalDate.now().plusMonths(1))) {
                    Optional<Property> propertyOptional = propertyRepositry.findById(lease.getPropertyId());
                    propertyOptional.ifPresent(property -> expiredLease.put(tenant, property));
                    break; //at least one

                }
            }
        }

        return expiredLease;
    }
    //--------------


//----------------------------------------  DONE DONE DONE DONE DONE  -------------------------

    public Map<Tenant, List<Lease>> findTenantsWithmultipleLeases() {
        Map<Tenant, List<Lease>> multipleLeases = new HashMap<>();

        for (Tenant tenant : tenantRepositry.findAll()) {
            List<Lease> leases = leaseRepositry.findAllByTenantId(tenant.getId());
            if (!leases.isEmpty()) {
                multipleLeases.put(tenant, leases);
            }
        }

        return multipleLeases;


    }

}
