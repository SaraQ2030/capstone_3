package com.example.testcapstone3.Service;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.Task;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.ClientRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
      private final CasseRepository caseRepository;
private final ClientRepository clientRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user){
        User u = userRepository.findUserById(id);
        if (u == null){
            throw new APIException("user not found!");
        }
        u.setEmail(user.getEmail());
        u.setName(user.getName());
       // u.setTasks(user.getTasks());
        u.setYearsOfExperience(user.getYearsOfExperience());
        u.setSpecialty(user.getSpecialty());
        u.setRole(user.getRole());
        u.setLawyerlicense(user.getLawyerlicense());
        userRepository.save(u);
    }

    public void deleteUser(Integer id){
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new APIException("user not found!");
        }
        userRepository.delete(user);
    }
//==============================

    public List<Casse> findCasseByUsserIdAndsAndStatus(Integer lawerID,String status){
        User user=userRepository.findUserById(lawerID);
        if (user==null){throw new APIException("Lawyer Not Found");}
        List<Casse> casses=caseRepository.findCasseByUsserIdAndsAndStatus(lawerID,status);
        if (casses.isEmpty()){throw new APIException("Empty List ");
        }
        return casses;
    }
    public void assignClientToUser(Integer clientID,Integer lawyerID){
        Client client=clientRepository.findClientById(clientID);
        User user=userRepository.findUserById(lawyerID);
        if (client==null|| user==null)throw new APIException ("can't assign ");
        client.getUsers().add(user);
        user.getClient().add(client);
        clientRepository.save(client);
        userRepository.save(user);
    }


    //method to get a lawyer by Specialty(get lawyers with their specialty)!
    public List<User> getLawyersBySpecialty(String specialty) {
        return userRepository.findUsersByRoleAndSpecialty("Lawyer", specialty);
    }

    //method to get assigned cases by lawyer id!
    public List<Casse> getAssignedCasesByLawyerId(Integer lawyerId) {
        return caseRepository.findCassesByUsserId(lawyerId);
    }


    //method to get top 10 lawyers depand on their experience
    public List<User> findTop10LawyersByExperience() {
        return userRepository.findTop10LawyersByExperience("Lawyer");
    }


    // Method to get the number of clients for specific lawyer
    public int getNumberOfClientsForLawyer(Integer lawyerId) {
        User lawyer = userRepository.findUserById(lawyerId);
        if (lawyer == null || !"Lawyer".equals(lawyer.getRole())) {
            throw new APIException("Lawyer not found!");
        }
        return lawyer.getClient().size();
    }


    // Method to get all users with a specific number of tasks
    public int getTaskCountByUserId(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new APIException("User not found!");
        }
        return user.getTasks().size();
    }


}
