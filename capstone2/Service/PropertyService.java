package com.example.capstone2.Service;

import com.example.capstone2.ApiResponse.APIException;
import com.example.capstone2.Model.Lease;
import com.example.capstone2.Model.MaintenanceRequest;
import com.example.capstone2.Model.Property;
import com.example.capstone2.Model.Tenant;
import com.example.capstone2.Repositry.LeaseRepositry;
import com.example.capstone2.Repositry.MaintenanceRequestRepositry;
import com.example.capstone2.Repositry.PropertyRepositry;
import com.example.capstone2.Repositry.TenantRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepositry propertyRepositry;
    private final LeaseRepositry leaseRepositry;
    private final MaintenanceRequestRepositry maintenanceRequestRepositry;
private  final TenantRepositry tenantRepositry;
    public List<Property> getAll() {
        if (propertyRepositry.findAll().isEmpty())
        {  throw new APIException("Empty Property");}
        return propertyRepositry.findAll();
    }

    public void add(Property property) {
        propertyRepositry.save(property);
    }

    public void update(Integer id, Property property) {
       if (getByID(id) == null)
       {  throw new APIException("Property with ID " + id + " not found");}

           Property retriveProperty = getByID(id);
           retriveProperty.setRentPrice(property.getRentPrice());
           retriveProperty.setArea(property.getArea());
           retriveProperty.setAddress(property.getAddress());
           retriveProperty.setType(property.getType());
           retriveProperty.setBathrooms(property.getBathrooms());
           retriveProperty.setBedrooms(property.getBedrooms());
           propertyRepositry.save(retriveProperty);

    }

    public void delete(Integer id) {
        Property property=propertyRepositry.findPropertyById(id);
        if (property== null)
        {  throw new APIException("Property with ID " + id + " not found");
           }
       else   propertyRepositry.delete(property);
    }

    public Property getByID(Integer id){
        if (propertyRepositry.findPropertyById(id)==null)
        {            throw new APIException("Property with ID " + id + " not found");}
        return propertyRepositry.findPropertyById(id);

    }

    //----------------------------  1 ------------------------------
    ///             DONE                    DONE                    DONE
    public Map<String, Double> calculateAverageRentPrice() {
        Map<String, Double> averageRent = new HashMap<>();

        if (propertyRepositry.findAll().isEmpty()) {
            throw new APIException("No properties found to calculate average rent price.");
        }
        double totalpricee = propertyRepositry.findAll().stream().mapToDouble(Property::getRentPrice).sum();
        totalpricee= totalpricee / propertyRepositry.findAll().size();
        averageRent.put("Average Rent Price for hole system",totalpricee);
        return averageRent;
    }
    //-------------------------------------  2-------------------------------
    ///             DONE                    DONE                    DONE

    public Map<String, String> AverageRenforechaType() {
        if (propertyRepositry.findAll().isEmpty()) {
            throw new APIException("Empty property.");
        }
        Map<String, Double> averageRentByType = new HashMap<>();
        Map<String, Double> totalRentByType = new HashMap<>();
        Map<String, Integer> propertyCountByType = new HashMap<>();
        Map<String, String> back = new HashMap<>();

        for (Property property : propertyRepositry.findAll()) {
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
            double totalRent = totalRentByType.get(type);
            int propertyCount = propertyCountByType.get(type);
            double averageRent = totalRent / propertyCount;
            DecimalFormat df = new DecimalFormat("#.##");
            String format=df.format(averageRent);
            averageRentByType.put(type,averageRent) ;
            back.put(type,df.format(averageRent));
        }
        return back;
    }

    //--------------------------- 3 ----------------------------------
    ///             DONE                    DONE                    DONE

    public List<Property> findfreeProperties() {
        List<Property> emptyProperties = new ArrayList<>();

        for (Property property : propertyRepositry.findAll()) {
            List<Lease> leases = leaseRepositry.findLeasesByPropertyId(property.getId());
            if (leases.isEmpty()) {
                emptyProperties.add(property);
            } else {
                boolean leasesExpired = leases.stream().allMatch(lease -> lease.getEndDate().isBefore(LocalDate.now()));
                if (leasesExpired) {
                    emptyProperties.add(property);

                }
            }
        }
        if (emptyProperties.isEmpty()){throw new APIException("Empty free Properties");}
        return emptyProperties;
}
///-----------------------------------  4 ----------------------------------
    ///             DONE                    DONE                    DONE

    public List<Property> getPendingRequests() {
        List<Property> pendingRequests = new ArrayList<>();
        for (Property property : propertyRepositry.findAll()) {
            List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepositry.findAllByPropertyId(property.getId());
            for (MaintenanceRequest request : maintenanceRequests) {
                if (request.getStatus().equalsIgnoreCase("PENDING")) {
                    long countdays =  LocalDate.now().toEpochDay() - request.getDateRequested().toEpochDay();//  covert to days
                    if (countdays > 30) {
                        pendingRequests.add(property);
                    }
                }
            }
        }
        if (pendingRequests.isEmpty()){
            throw new APIException("Empty");
        }
        return pendingRequests;
    }

    //--------------------------- 5 ----------------------------
// /           DONE                    DONE                    DONE
    //--------------------
    public List<Property> getshhortLeases() {
        List<Property> shortLeases = new ArrayList<>();

        for (Property property : propertyRepositry.findAll()) {
            List<Lease> leases = leaseRepositry.findLeasesByPropertyId(property.getId());

            if (!leases.isEmpty()) {
                long totalLeaseDays=0;
                for (Lease lease : leases) {
                    totalLeaseDays= calculateDays(lease.getStartDate(), lease.getEndDate());

                    if (totalLeaseDays < 12) {// 12 mid-month
                        shortLeases.add(property);
                    }


                }
            }
        }
        if (shortLeases.isEmpty()){
            throw new APIException("Empty short Leases");
        }
        return shortLeases;
    }
    //--------------

    private long calculateDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

//-------------------------------------------- 6 --------------------------
    ///             DONE                    DONE                    DONE

    public Map<Property, Integer> calculateTotalRevenueByProperty(LocalDate startDate, LocalDate endDate) {
        Map<Property, Integer> totalRevenue = new HashMap<>();

        List<Lease> leasesList = leaseRepositry.findByStartDateBetweenAndEndDateBetween(startDate, endDate, startDate, endDate);

        for (Lease lease : leasesList) {
            totalRevenue.merge(propertyRepositry.findPropertyById(lease.getPropertyId()), lease.getRentAmount(), Integer::sum);
        }
        return totalRevenue;
    }
}





