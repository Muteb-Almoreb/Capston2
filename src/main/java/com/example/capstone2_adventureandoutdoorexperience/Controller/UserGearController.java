package com.example.capstone2_adventureandoutdoorexperience.Controller;


import com.example.capstone2_adventureandoutdoorexperience.API.ApiResponse;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Model.UserGear;
import com.example.capstone2_adventureandoutdoorexperience.Service.UserGearService;
import com.example.capstone2_adventureandoutdoorexperience.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userGear")
@RequiredArgsConstructor
public class UserGearController {

    private final UserGearService userGearService;


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserGear(@PathVariable Integer userId) {
        return ResponseEntity.ok(userGearService.getUserGear(userId));
    }

    // Add gear for a specific user
    @PostMapping("/addGear")
    public ResponseEntity<ApiResponse> addGear(@RequestBody @Valid UserGear userGear) {

        userGearService.addGear(userGear);
        return ResponseEntity.ok(new ApiResponse("Gear added successfully"));
    }

    @PostMapping("/addGears")
    public ResponseEntity<ApiResponse> addGears(@RequestBody @Valid List<UserGear> gears){
        userGearService.addGears(gears);
        return ResponseEntity.ok(new ApiResponse("User gears added successfully"));
    }



    // Update a gear name
    @PutMapping("/updateGear/{gearId}/{newName}")
    public ResponseEntity<ApiResponse> updateGear(@PathVariable Integer gearId,@PathVariable String newName) {
        userGearService.update(gearId, newName);
        return ResponseEntity.ok(new ApiResponse("Gear updated successfully"));
    }

    // Delete a gear
    @DeleteMapping("/deleteGear/{userId}/{gearName}")
    public ResponseEntity<ApiResponse> deleteGear(@PathVariable Integer userId,@PathVariable String gearName) {

        userGearService.deleteGear(userId, gearName);
        return ResponseEntity.ok(new ApiResponse("Gear deleted successfully"));
    }


}
