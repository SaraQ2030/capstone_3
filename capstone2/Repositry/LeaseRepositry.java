package com.example.capstone2.Repositry;

import com.example.capstone2.Model.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public  interface LeaseRepositry extends JpaRepository<Lease,Integer> {
    @Query("select l from Lease l where l.id=?1")
    Lease findByID(Integer id);
    Lease findByPropertyId(Integer id);
    List<Lease> findLeasesByPropertyId(Integer id);
    @Query("select l from Lease l where l.propertyId=?1")
    Lease getLeaseByPropertyId(Integer id);
   List<Lease> findAllByTenantId(Integer id);

    List<Lease> findByStartDateBetweenAndEndDateBetween(LocalDate satrtDate1, LocalDate endDate1,LocalDate satrtDate2, LocalDate endDate2);
    Lease findLeasesByTenantId(Integer tenantId);
}
