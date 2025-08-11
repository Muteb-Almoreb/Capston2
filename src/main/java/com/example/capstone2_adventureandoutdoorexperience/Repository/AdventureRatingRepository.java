package com.example.capstone2_adventureandoutdoorexperience.Repository;

import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdventureRatingRepository extends JpaRepository<AdventureRating , Integer> {

    List<AdventureRating> findByAdventureId(Integer adventureId);


    Boolean existsByAdventureIdAndUserId(Integer adventureId, Integer userId);

    @Query("select avg(r.score) from AdventureRating r where r.adventureId =?1")
    Double avgScore(Integer adventureId);


    AdventureRating findAdventureRatingById(Integer id);


}

