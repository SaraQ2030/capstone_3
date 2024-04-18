package com.example.capstone2.Repositry;

import com.example.capstone2.Model.MaintenanceRequest;
import com.example.capstone2.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRequestRepositry extends JpaRepository<MaintenanceRequest,Integer> {
    @Query("select m from MaintenanceRequest m where m.id=?1")
    MaintenanceRequest findByID(Integer id);
    List<MaintenanceRequest> findAllByTenantIdAndAndStatus(Integer tenantID, String Status );
    List<MaintenanceRequest> findAllByPropertyId(Integer id);
    MaintenanceRequest findMaintenanceRequestByIdAndAndTenantId(Integer id,Integer tenID);
}
