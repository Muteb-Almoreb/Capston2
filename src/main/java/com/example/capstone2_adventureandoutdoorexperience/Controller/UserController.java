package com.example.capstone2_adventureandoutdoorexperience.Controller;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiResponse;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<?> geAllUser(){
        return ResponseEntity.status(200).body(userService.getAllUser());
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user){
        userService.addUser(user);
       return ResponseEntity.status(200).body(new ApiResponse("The User is added Successfully"));
    }

    @PostMapping("/addUsers")
    public ResponseEntity<ApiResponse> addUsers(@RequestBody @Valid List<User> users) {
        userService.addUsers(users);
        return ResponseEntity.ok(new ApiResponse("Users added successfully"));
    }


    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id , @Valid @RequestBody User user){
        userService.updateUser(id , user);
        return ResponseEntity.status(200).body(new ApiResponse("The User is updated Successfully"));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){

        userService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("The User is deleted Successfully"));
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(userService.getUserById(id));
    }


}
