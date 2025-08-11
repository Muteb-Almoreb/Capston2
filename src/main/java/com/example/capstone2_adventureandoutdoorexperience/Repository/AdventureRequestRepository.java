package com.example.capstone2_adventureandoutdoorexperience.Repository;

import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRequestRepository extends JpaRepository <AdventureRequest ,Integer> {

    AdventureRequest findAdventureRequestById(Integer id);

    List<AdventureRequest> findAdventureRequestByAdventureId(Integer adventureId);

    List<AdventureRequest> findAdventureRequestByUserId(Integer userId);




    List<AdventureRequest> findByUserIdAndAdventureId(Integer userId, Integer adventureId);

    Boolean existsByUserIdAndAdventureIdAndStatusIgnoreCase(Integer userId, Integer adventureId, String status);

}


