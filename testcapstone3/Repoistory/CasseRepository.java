package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Casse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasseRepository extends JpaRepository<Casse,Integer> {

    Casse findCasseById(Integer id);
    @Query("select c from  Casse  c where c.usser.id=?1 and c.status=?2")
    List<Casse> findCasseByUsserIdAndsAndStatus(Integer lawyerID,String s);


    List<Casse> findCassesByUsserId(Integer lawyerId);

    List<Casse> findCassesByTypeOflawsuits(String typeOfLawsuits);
}
