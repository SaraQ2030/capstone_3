package com.example.testcapstone3.Service;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.DTO.UserDTO;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.Task;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.ClientRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//==============================EXTRA=========================================================
    //extra 1           ghaliah
    public List<Casse> findCasseByUsserIdAndsAndStatus(Integer lawerID,String status){
        User user=userRepository.findUserById(lawerID);
        if (user==null){throw new APIException("Lawyer Not Found");}
        List<Casse> casses=caseRepository.findCasseByUsserIdAndsAndStatus(lawerID,status);
        if (casses.isEmpty()){throw new APIException("Empty List ");
        }
        return casses;
    }

    //extra 2 ghaliah
    public void assignClientToUser(Integer clientID,Integer lawyerID){
        Client client=clientRepository.findClientById(clientID);
        User user=userRepository.findUserById(lawyerID);
        if (client==null|| user==null)throw new APIException ("can't assign ");
        client.getUsers().add(user);
        user.getClient().add(client);
        clientRepository.save(client);
        userRepository.save(user);
    }


    //extra 3
    //method to get a lawyer by Specialty(get lawyers with their specialty)!   abdurahman
    public List<User> getLawyersBySpecialty(String specialty) {

        return userRepository.findUsersByRoleAndSpecialty("Lawyer", specialty);
    }

    //extra 4
    //method to get assigned cases by lawyer id! abdulrahman
    public List<Casse> getAssignedCasesByLawyerId(Integer lawyerId) {
        return caseRepository.findCassesByUsserId(lawyerId);
    }


    //extra 5
    //method to get top 10 lawyers depand on their experience
    //abdurahman

    public List<User> findTop10LawyersByExperience() {
        return userRepository.findTop10LawyersByExperience("Lawyer");
    }


    //extra 6
    // Method to get the number of clients for specific lawyer
    public int getNumberOfClientsForLawyer(Integer lawyerId) {
        User lawyer = userRepository.findUserById(lawyerId);
        if (lawyer == null || !"Lawyer".equals(lawyer.getRole())) {
            throw new APIException("Lawyer not found!");
        }
        return lawyer.getClient().size();
    }


    //extra 7
    // Method to get all users with a specific number of tasks
    //sarah
    public int getCasesCountByUserId(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new APIException("User not found!");
        }
        return user.getCasses().size();
    }



//    //get top 10 lawyer depend on their rating
//    public List<UserDTO> getTopRatedLawyers() {
//        List<User> allLawyers = userRepository.findAll();
//        List<UserDTO> userDTOS=new ArrayList<>();
//        allLawyers.sort((lawyer1, lawyer2) -> Double.compare(lawyer2.getAverageRating(), lawyer1.getAverageRating()));
//        for (User user:allLawyers){
//            for (UserDTO userDTO:userDTOS){
//                userDTO.setName(user.getName());
//                userDTO.setLawyerlicense(user.getLawyerlicense());
//                userDTO.setYearsOfExperience(user.getYearsOfExperience());
//                userDTO.setSpecialty(user.getSpecialty());
//                userDTOS.add(userDTO);
//            }
//        }
//        return userDTOS;
//    }

    //extra 8
    public List<User> getTopRatedLawyers() {
        List<User> allLawyers = userRepository.findAllByRole("Lawyer");
        allLawyers.sort((lawyer1, lawyer2) -> Double.compare(lawyer2.getAverageRating(), lawyer1.getAverageRating()));
        return allLawyers.stream().limit(10).collect(Collectors.toList());
    }


    //extra 9
    //write a method to count how many lawyer wins
public List<User> getTopWinnwer(){
        List<User> list=userRepository.findAll();
        List<User>returnList=new ArrayList<>();
        for(User user:list){
            if (user.getCount()>=1){
                returnList.add(user);
            }
        }return returnList;
}

    //extra 10
    public Double getAverge(Integer lawyerId){
        User lawyer = userRepository.findUserById(lawyerId);
        if(lawyer==null){
            throw new APIException("Lawyer not found!");
        }
        double sum = 0;
        for (double rate : lawyer.getRatings()) {
            sum += rate;
        }
        double averageRating = sum / lawyer.getRatings().size();
        lawyer.setAverageRating(averageRating);
        userRepository.save(lawyer);
        return averageRating; }
}
