package com.example.testcapstone3.Service;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.TaskRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //    private final CaseRepository caseRepository;
    private final TaskRepository taskRepository;

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
        u.setTasks(user.getTasks());
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




    //method to get a lawyer by Specialty(retrive lawyers with thier specialty)!
//    public List<User> getLawyersBySpecialty(String specialty) {
//        return userRepository.findUsersByRoleAndSpecialty("Lawyer", specialty);
//    }
    //method to get assigned cases by lawyer id!
//    public List<Case> getAssignedCasesByLawyerId(Integer lawyerId) {
//        return caseRepository.findCasesByLawyerId(lawyerId);
//    }
    /*getTasksByUserAndStatus(Integer userId, String status)
    Retrieve tasks of a specific status assigned to a user.
     */
//    public List<Task> getTasksByUserAndStatus(Integer userId, String status) {
//        return taskRepository.findTasksByUserIdAndStatus(userId, status);
//    }

}
