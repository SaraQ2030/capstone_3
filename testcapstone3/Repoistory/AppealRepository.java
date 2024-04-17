package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRepository extends JpaRepository<Appeal,Integer> {
    Appeal findAppealByCasseId(Integer id);
}
