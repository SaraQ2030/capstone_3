package com.example.capstone2.Repositry;

import com.example.capstone2.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepositry extends JpaRepository<Property,Integer> {
    @Query("select p from Property p where p.id=?1")
    Property findPropertyById(Integer id);
    Property getById(Integer id);
    List<Property> findPropertyByType(String type);
}
