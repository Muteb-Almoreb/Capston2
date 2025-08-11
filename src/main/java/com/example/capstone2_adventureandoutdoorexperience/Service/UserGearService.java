package com.example.capstone2_adventureandoutdoorexperience.Service;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiException;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Model.UserGear;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserGearRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGearService {

    private final UserRepository userRepository;

    private final UserGearRepository userGearRepository;

    public List<UserGear> getUserGear(Integer userId){

        if (userRepository.findUserById(userId) == null) {
            throw new ApiException("User not found");
        }
        return userGearRepository.findByUserId(userId);
    }


    public void addGear(UserGear userGear){
        User user = userRepository.findUserById(userGear.getUserId());
        if(user==null){
            throw new ApiException("User not found");
        }

        if (userGear.getGearName() == null || userGear.getGearName().isBlank()) {
            throw new ApiException("Gear name cannot be empty");
        }

        if (userGearRepository.existsByUserIdAndGearNameIgnoreCase(userGear.getUserId(), userGear.getGearName().trim())) {
            throw new ApiException("Gear already exists for this user");
        }


        userGearRepository.save(userGear);

    }


    public void addGears(List<UserGear> gears) {
        if (gears == null || gears.isEmpty()) return;

        int i = 0;
        for (UserGear g : gears) {
            i++;

            if (g.getUserId() == null)
                throw new ApiException("Item #" + i + ": User ID is required");

            User user = userRepository.findUserById(g.getUserId());
            if (user == null)
                throw new ApiException("Item #" + i + ": User not found");

            if (g.getGearName() == null || g.getGearName().isBlank())
                throw new ApiException("Item #" + i + ": Gear name cannot be empty");

            String name = g.getGearName().trim();
            if (userGearRepository.existsByUserIdAndGearNameIgnoreCase(g.getUserId(), name))
                throw new ApiException("Item #" + i + ": Gear already exists for this user");

            g.setGearName(name);
        }

        userGearRepository.saveAll(gears);
    }




    public void update(Integer gearId , String newName){
        if(newName==null || newName.isBlank()){
            throw new ApiException("Gear name cannot be empty");

        }

        UserGear gear = userGearRepository.findUserGearById(gearId);
        if(gear==null){
            throw new ApiException("Gear not found");

        }
        // منع التكرار لنفس المستخدم بنفس الاسم الجديد
        if (userGearRepository.existsByUserIdAndGearNameIgnoreCase(gear.getUserId(), newName.trim())) {
            throw new ApiException("Gear already exists for this user");
        }
        gear.setGearName(newName.trim());
        userGearRepository.save(gear);
    }


    public void deleteGear (Integer userId , String gearName){
    if(userRepository.findUserById(userId)==null){
        throw new ApiException("User not found");
    }
    if(gearName== null|| gearName.isBlank()){
        throw new ApiException("Gear name cannot be empty");
    }



        UserGear gear = userGearRepository.findByUserIdAndGearNameIgnoreCase(userId, gearName.trim());
        if (gear == null) throw new ApiException("Gear not found for this user");

        userGearRepository.delete(gear);
    }





    }


