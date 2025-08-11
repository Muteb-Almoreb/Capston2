package com.example.capstone2_adventureandoutdoorexperience.Controller;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiResponse;
import com.example.capstone2_adventureandoutdoorexperience.Model.Adventure;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Service.AdventureService;
import com.example.capstone2_adventureandoutdoorexperience.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adventure")
@RequiredArgsConstructor
public class AdventureController {


    private final AdventureService adventureService;

    @GetMapping("/getAllAdventure")
    public ResponseEntity<?> geAllAdventureService(){
        return ResponseEntity.status(200).body(adventureService.getAllAdventure());
    }

    @PostMapping("/addAdventure")
    public ResponseEntity<?> addAdventure(@Valid @RequestBody Adventure adventure , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        adventureService.addAdventure(adventure);
        return ResponseEntity.status(200).body(new ApiResponse("The Adventure is added Successfully"));
    }


    @PostMapping("/addAdventures")
    public ResponseEntity<ApiResponse> addAdventures(@RequestBody @Valid List<Adventure> adventures, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        adventureService.addAdventures(adventures);
        return ResponseEntity.ok(new ApiResponse("Adventures added successfully"));
    }


    @PutMapping("/updateAdventure/{id}")
    public ResponseEntity<?> updateAdventure(@PathVariable Integer id , @Valid @RequestBody Adventure adventure , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        adventureService.updateAdventure(id , adventure);
        return ResponseEntity.status(200).body(new ApiResponse("The Adventure is updated Successfully"));
    }

    @DeleteMapping("/deleteAdventure/{id}")
    public ResponseEntity<?> deleteAdventure(@PathVariable Integer id){

        adventureService.deleteAdventure(id);
        return ResponseEntity.status(200).body(new ApiResponse("The Adventure is deleted Successfully"));
    }


}
