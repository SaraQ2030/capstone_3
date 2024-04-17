package com.example.testcapstone3.Controller;
import com.example.testcapstone3.ApiResponse.APIResponse;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        logger.info("inside get all users!");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        logger.info("inside add user!");
        userService.addUser(user);
        return ResponseEntity.ok().body(new APIResponse("user added successfully!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user){
        logger.info("inside update user!");
        userService.updateUser(id, user);
        return ResponseEntity.ok().body(new APIResponse("user updated successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        logger.info("inside delete user!");
        userService.deleteUser(id);
        return ResponseEntity.ok().body(new APIResponse("user deleted successfully!"));
    }

    @GetMapping("get-all-with-status/{lawyerID}/{status}")
    public ResponseEntity findCasseByUsserIdAndsAndStatus(@PathVariable Integer lawyerID,@PathVariable String status)
    {return ResponseEntity.status(200).body(userService.findCasseByUsserIdAndsAndStatus(lawyerID,status));
    }

    @GetMapping("/get-lawyers-by-specialty/{specialty}")
    public ResponseEntity getLawyersBySpecialty(@PathVariable String specialty){
        logger.info("inside get lawyers by specialy");
        return ResponseEntity.ok().body(userService.getLawyersBySpecialty(specialty));
    }

    @GetMapping("/get-assigned-case-by-lawyer-id/{lawyerId}")
    public ResponseEntity getAssignedCasesByLawyerId(@PathVariable Integer lawyerId){
        logger.info("inside get Assigned Cases By LawyerId");
        return ResponseEntity.ok().body(userService.getAssignedCasesByLawyerId(lawyerId));
    }

    @GetMapping("get-top-10")
    public ResponseEntity findTop10LawyersByExperience(){
        logger.info("inside find top 10 lawyers");
        return ResponseEntity.ok().body(userService.findTop10LawyersByExperience());
    }

    @GetMapping("/get-number-of-clients-for-lawyer/{userId}")
    public ResponseEntity getNumberOfClientsForLawyer(@PathVariable Integer userId){
        logger.info("inside get number of clients");
        return ResponseEntity.ok().body(userService.getNumberOfClientsForLawyer(userId));
    }

    @GetMapping("/get-tasks-by-user/{userId}")
    public ResponseEntity getTaskCountByUserId(@PathVariable Integer userId){
        logger.info("inside get Task Count By UserId");
        return ResponseEntity.ok().body(userService.getTaskCountByUserId(userId));
    }

}
