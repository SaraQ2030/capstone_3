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
}
