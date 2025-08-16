package com.example.capstone2_adventureandoutdoorexperience.Controller;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiResponse;
import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRequest;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRequestRepository;
import com.example.capstone2_adventureandoutdoorexperience.Service.AdventureRequestService;
import com.example.capstone2_adventureandoutdoorexperience.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/AdventureRequest")
@RequiredArgsConstructor
public class AdventureRequestController {

    private final AdventureRequestService adventureRequestService;

    @GetMapping("/getAllAdventureRequest")
    public ResponseEntity<?> getAllAdventureRequest(){
        return ResponseEntity.status(200).body(adventureRequestService.getAllAdventureRequest());
    }



    @PostMapping("/addAdventureRequest")
    public ResponseEntity<?> addAdventureRequest(@Valid @RequestBody AdventureRequest adventureRequest){

        adventureRequestService.addAdventureRequest(adventureRequest);
        return ResponseEntity.status(200).body(new ApiResponse("The AdventureRequest is added Successfully"));
    }


    @PostMapping("/addAdventureRequests")
    public ResponseEntity<ApiResponse> addAdventureRequests(@RequestBody @Valid List<AdventureRequest> requests){
        adventureRequestService.addAdventureRequests(requests);
        return ResponseEntity.ok(new ApiResponse("Adventure requests added successfully"));
    }


    @PutMapping("/updateAdventureRequest/{id}")
    public ResponseEntity<?> updateAdventureRequest(@PathVariable Integer id , @Valid @RequestBody AdventureRequest adventureRequest){
        adventureRequestService.updateAdventureRequest(id ,adventureRequest);
        return ResponseEntity.status(200).body(new ApiResponse("The AdventureRequest is updated  Successfully"));
    }

    @DeleteMapping("/deleteAdventureRequest/{id}")
    public ResponseEntity<?> deleteAdventureRequest(@PathVariable Integer id){

            adventureRequestService.deleteAdventureRequest(id);
        return ResponseEntity.status(200).body(new ApiResponse("The AdventureRequest is deleted Successfully"));
    }

    @GetMapping("getByUser/{id}")
    public ResponseEntity<?> getByUser(@PathVariable Integer id){

        return ResponseEntity.status(200).body(adventureRequestService.getByUser(id));
    }
    @GetMapping("getByAdventure/{adventureId}")
    public ResponseEntity<?> getByAdventure(@PathVariable Integer adventureId){
        return ResponseEntity.status(200).body(adventureRequestService.getByAdventure(adventureId));
    }

    @GetMapping("/getRequestsForAdventure/{adventureId}/{userId}")
    public ResponseEntity<?> getRequestsForAdventure(
            @PathVariable Integer adventureId,
            @PathVariable Integer userId) {

        return ResponseEntity.ok(adventureRequestService.getRequestsForAdventure(adventureId, userId));
    }



    @PutMapping("/approve/{requestId}/{callerId}")
    public ResponseEntity<?> approve(@PathVariable Integer requestId,
                                     @PathVariable Integer callerId) {
        adventureRequestService.approveRequest(callerId, requestId);
        return ResponseEntity.ok(new ApiResponse("Request approved"));
    }

    @PutMapping("/reject/{requestId}/{callerId}")
    public ResponseEntity<?> reject(@PathVariable Integer requestId,
                                    @PathVariable Integer callerId) {
        adventureRequestService.rejectRequest(callerId, requestId);
        return ResponseEntity.ok(new ApiResponse("Request rejected"));
    }

    @PutMapping("/pay/{requestId}/{userId}")
    public ResponseEntity<?> pay(@PathVariable Integer requestId,
                                 @PathVariable Integer userId) {
        adventureRequestService.pay(userId, requestId);
        return ResponseEntity.ok(new ApiResponse("Payment marked as PAID"));
    }

    @PutMapping("/release/{requestId}/{userId}")
    public ResponseEntity<?> release(@PathVariable Integer requestId,
                                     @PathVariable Integer userId) {
        adventureRequestService.releasePayment(userId, requestId);
        return ResponseEntity.ok(new ApiResponse("Payment released to the adventure creator"));
    }







    @GetMapping("/getRequestsForAdventureFiltered/{adventureId}/{ownerId}")
    public ResponseEntity<?> getAllForOwner(@PathVariable Integer adventureId,
                                            @PathVariable Integer ownerId) {
        return ResponseEntity.ok(adventureRequestService.getRequestsForAdventureAll(ownerId, adventureId));
    }





    @GetMapping("/getRequestsForAdventureFiltered/{adventureId}/{ownerId}/{status}")
    public ResponseEntity<?> getByStatusForOwner(@PathVariable Integer adventureId,
                                                 @PathVariable Integer ownerId,
                                                 @PathVariable String status) {
        return ResponseEntity.ok(adventureRequestService.getRequestsForAdventureByStatus(ownerId, adventureId, status));
    }


    @PutMapping("/attend/{requestId}/{ownerId}")
    public ResponseEntity<?> markAttended(@PathVariable Integer requestId,
                                          @PathVariable Integer ownerId) {
        adventureRequestService.markAttended(ownerId, requestId);
        return ResponseEntity.ok(new ApiResponse("Attendance marked: attended"));
    }

    @PutMapping("/absent/{requestId}/{ownerId}")
    public ResponseEntity<?> markAbsent(@PathVariable Integer requestId,
                                        @PathVariable Integer ownerId) {
        adventureRequestService.markAbsent(ownerId, requestId);
        return ResponseEntity.ok(new ApiResponse("Attendance marked: absent"));
    }





}
