package com.example.capstone2.Repositry;

import com.example.capstone2.Model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface TenantRepositry extends JpaRepository<Tenant,Integer> {
    @Query("select t from Tenant t where t.id=?1")
    Tenant findByID(Integer id);
    Tenant findTenantById(Integer id);
    //findByID
}
