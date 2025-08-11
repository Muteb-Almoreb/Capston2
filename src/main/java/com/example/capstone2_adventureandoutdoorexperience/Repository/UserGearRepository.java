package com.example.capstone2_adventureandoutdoorexperience.Repository;

import com.example.capstone2_adventureandoutdoorexperience.Model.UserGear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGearRepository  extends JpaRepository <UserGear , Integer> {

    UserGear findUserGearById(Integer id);

    List<UserGear> findByUserId(Integer userId);

    Boolean existsByUserIdAndGearNameIgnoreCase(Integer userId , String gearName);

    UserGear findByUserIdAndGearNameIgnoreCase(Integer userId, String gearName);

    Boolean existsByIdAndUserId(Integer id, Integer userId);




}
