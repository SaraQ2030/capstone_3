package com.example.testcapstone3.Repoistory;

import com.example.testcapstone3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);

    List<User> findUsersByRoleAndSpecialty(String role, String specialty);


    @Query(value = "SELECT u FROM User u WHERE u.role = 'Lawyer' ORDER BY u.yearsOfExperience DESC LIMIT 10")
    List<User> findTop10LawyersByExperience(String role);



    List<User> findAllByRole(String Role);

}
