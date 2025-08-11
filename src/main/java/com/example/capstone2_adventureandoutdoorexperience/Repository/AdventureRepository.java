package com.example.capstone2_adventureandoutdoorexperience.Repository;

import com.example.capstone2_adventureandoutdoorexperience.Model.Adventure;
import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository <Adventure, Integer> {

    Adventure findAdventureById(Integer id);



}
