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
//9
    @GetMapping("/get-all-with-status/{lawyerID}/{status}")
    public ResponseEntity findCasseByUsserIdAndsAndStatus(@PathVariable Integer lawyerID,@PathVariable String status)
    {return ResponseEntity.status(200).body(userService.findCasseByUsserIdAndsAndStatus(lawyerID,status));
    }
//8
    @GetMapping("/get-lawyers-by-specialty/{specialty}")
    public ResponseEntity getLawyersBySpecialty(@PathVariable String specialty){
        logger.info("inside get lawyers by specialy");
        return ResponseEntity.ok().body(userService.getLawyersBySpecialty(specialty));
    }
//7
    @GetMapping("/get-assigned-case-by-lawyer-id/{lawyerId}")
    public ResponseEntity getAssignedCasesByLawyerId(@PathVariable Integer lawyerId){
        logger.info("inside get Assigned Cases By LawyerId");
        return ResponseEntity.ok().body(userService.getAssignedCasesByLawyerId(lawyerId));
    }
//6
    @GetMapping("/get-top-10-by-experience")
    public ResponseEntity findTop10LawyersByExperience(){
        logger.info("inside find top 10 lawyers");
        return ResponseEntity.ok().body(userService.findTop10LawyersByExperience());
    }
//5
    @GetMapping("/get-number-of-clients-for-lawyer/{userId}")
    public ResponseEntity getNumberOfClientsForLawyer(@PathVariable Integer userId){
        logger.info("inside get number of clients");
        return ResponseEntity.ok().body(userService.getNumberOfClientsForLawyer(userId));
    }
//4
    @GetMapping("/get-casses-by-user/{userId}")
    public ResponseEntity getCaseCountByUserId(@PathVariable Integer userId){
        logger.info("inside get cases Count By UserId");
        return ResponseEntity.ok().body(userService.getCasesCountByUserId(userId));
    }
//3
    @GetMapping("/get-top10-lawyer-by-rating")
    public ResponseEntity getTopRatedLawyers(){
        logger.info("insdie get top 10 lawyer depend on their rating");
        return ResponseEntity.ok().body(userService.getTopRatedLawyers());
    }

    //2getTopWinnwer
    @GetMapping("/get-top-winnwer")
    public ResponseEntity getTopWinnwer(){
        logger.info("insdie get top 10 lawyer depend on winner");
        return ResponseEntity.ok().body(userService.getTopWinnwer());
    }//1
@GetMapping("get-averge/{lawyerId}")
    public ResponseEntity getAverage(@PathVariable Integer lawyerId){
        logger.info("inside get average");
        return ResponseEntity.ok().body(userService.getAverge(lawyerId));
    }






}
