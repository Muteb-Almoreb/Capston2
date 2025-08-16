package com.example.capstone2_adventureandoutdoorexperience.Controller;


import com.example.capstone2_adventureandoutdoorexperience.API.ApiResponse;
import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRating;
import com.example.capstone2_adventureandoutdoorexperience.Service.AdventureRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adventureRating")
public class AdventureRatingController {


    private final AdventureRatingService adventureRatingService;

    @GetMapping("/getByAdventure/{adventureId}")
    public ResponseEntity<?> getByAdventure(@PathVariable Integer adventureId){
        return ResponseEntity.status(200).body(adventureRatingService.getByAdventure(adventureId));
    }



    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody AdventureRating rating){

        adventureRatingService.add(rating);
        return ResponseEntity.status(200).body(new ApiResponse("The Rating is add successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody AdventureRating rating){

        adventureRatingService.update(id, rating);

        return ResponseEntity.status(200).body(new ApiResponse("The Rating is updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){

        adventureRatingService.delete(id);

        return ResponseEntity.status(200).body(new ApiResponse("The Rating is deleted successfully"));
    }



    @GetMapping("/getAverage/{adventureId}")
    public ResponseEntity<?> getAverage(@PathVariable Integer adventureId){

        return ResponseEntity.status(200).body(adventureRatingService.getAverage(adventureId));
    }
}

