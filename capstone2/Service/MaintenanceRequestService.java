package com.example.capstone2.Service;

import com.example.capstone2.ApiResponse.APIException;
import com.example.capstone2.Model.MaintenanceRequest;
import com.example.capstone2.Repositry.MaintenanceRequestRepositry;
import com.example.capstone2.Repositry.PropertyRepositry;
import com.example.capstone2.Repositry.TenantRepositry;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {
    private final MaintenanceRequestRepositry maintenanceRequestRepositry;
    private final PropertyRepositry propertyRepositry;
    private final TenantRepositry tenantRepositry;

    public List<MaintenanceRequest> getall() {
        if (maintenanceRequestRepositry.findAll().isEmpty())
            throw new APIException("Empty list maintenanceRequest");
        return maintenanceRequestRepositry.findAll();
    }

    public void add(MaintenanceRequest maintenanceRequest) {
        if (tenantRepositry.findByID(maintenanceRequest.getTenantId()) == null) {
            throw new APIException("Tenant ID not found");
        }
            if (propertyRepositry.findPropertyById(maintenanceRequest.getPropertyId()) == null) {
                throw new APIException("Property ID not found");
            }
                maintenanceRequest.setDateRequested(LocalDate.now());//start
                maintenanceRequest.setStatus("PENDING");//start status
                maintenanceRequestRepositry.save(maintenanceRequest);

        }


    public void update(Integer id, MaintenanceRequest maintenanceRequest) {
        if (maintenanceRequestRepositry.findByID(id) == null) {
            throw new APIException("MaintenanceRequest with ID " + id + " not found");
        }
            MaintenanceRequest request = maintenanceRequestRepositry.findByID(id);
            request.setPropertyId(maintenanceRequest.getPropertyId());
            request.setDateRequested(maintenanceRequest.getDateRequested());//no need update to locaDate
            // because i have method resendRequest
            request.setStatus(maintenanceRequest.getStatus());
            request.setDescription(maintenanceRequest.getDescription());
            request.setTenantId(maintenanceRequest.getTenantId());
            maintenanceRequestRepositry.save(request);

    }

    public void delete(Integer id) {
        if (maintenanceRequestRepositry.findByID(id) == null) {
            throw new APIException("MaintenanceRequest with ID " + id + " not found");
        }
            maintenanceRequestRepositry.delete(maintenanceRequestRepositry.findByID(id));
    }

    public MaintenanceRequest getByID(Integer id) {
        if (maintenanceRequestRepositry.findByID(id) == null) {
            throw new APIException("MaintenanceRequest with ID " + id + " not found");
        }
            return maintenanceRequestRepositry.findByID(id);
        }



    //------------------------------- 1 --- change Status Maintenance Request-------------------
    public void changeStatusRequest(Integer id) {
        if (maintenanceRequestRepositry.findByID(id) == null) {
            throw new APIException("MaintenanceRequest with ID " + id + " not found");
        }
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepositry.findByID(id);
        if (maintenanceRequest.getStatus().equalsIgnoreCase("PENDING")) {
            maintenanceRequest.setStatus("IN_PROGRESS");
            maintenanceRequestRepositry.save(maintenanceRequest);
        }//start status

        else if (maintenanceRequest.getStatus().equalsIgnoreCase("IN_PROGRESS")) {
            maintenanceRequest.setStatus("COMPLETED");
            maintenanceRequestRepositry.save(maintenanceRequest);

        } else {
            throw new APIException("MaintenanceRequest with ID " + id + " already complete");
        }
    }

    //---------------------------- 2

    public List<MaintenanceRequest> giveAllRequest(Integer tenantID, String status) {

        if (tenantRepositry.findByID(tenantID) == null) {
            throw new APIException("Tenant ID not found");
        }
        if (maintenanceRequestRepositry.findAllByTenantIdAndAndStatus(tenantID, status).isEmpty()) {
            throw new APIException("No Maintenance Request with Status " + status);
        }
       return maintenanceRequestRepositry.findAllByTenantIdAndAndStatus(tenantID, status);

    }

    //----------------------------------  3 -------------------------------


}