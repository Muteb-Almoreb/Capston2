package com.example.capstone2_adventureandoutdoorexperience.Service;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiException;
import com.example.capstone2_adventureandoutdoorexperience.Model.Adventure;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdventureService {


    private final AdventureRepository adventureRepository;

    private final UserRepository userRepository;

    public List<Adventure> getAllAdventure(){
        return adventureRepository.findAll();
    }

    public void addAdventure(Adventure adventure){
        if(adventure.getCreatorId() == null){
            throw new ApiException("Creator ID must be provided");
        }


         User creator = userRepository.findUserById(adventure.getCreatorId());
         if (creator == null) throw new ApiException("Creator not found");


        validateCompliance(adventure);


        adventureRepository.save(adventure);
    }

    public void addAdventures(List<Adventure> adventures){
        if (adventures == null || adventures.isEmpty()) return;

        int i = 0;
        for (Adventure a : adventures){
            i++;

            if (a.getCreatorId() == null) {
                throw new ApiException("Creator ID must be provided for item #" + i);
            }

            User creator = userRepository.findUserById(a.getCreatorId());
            if (creator == null) {
                throw new ApiException("Creator not found for item #" + i + " (creatorId=" + a.getCreatorId() + ")");
            }


            validateCompliance(a);
        }

        adventureRepository.saveAll(adventures);
    }





    public void updateAdventure(Integer id, Adventure adventure){
        Adventure oldAdventure = adventureRepository.findAdventureById(id);
        if(oldAdventure == null){
            throw new ApiException("The Adventure Not found");
        }

        oldAdventure.setDate(adventure.getDate());
        oldAdventure.setDescription(adventure.getDescription());
        oldAdventure.setLocation(adventure.getLocation());
        oldAdventure.setAdventureLocationUrl(adventure.getAdventureLocationUrl());
        oldAdventure.setTitle(adventure.getTitle());
        oldAdventure.setGenderRestriction(adventure.getGenderRestriction());
        oldAdventure.setCostPerPerson(adventure.getCostPerPerson());
        oldAdventure.setMeetingPointUrl(adventure.getMeetingPointUrl());
        oldAdventure.setTotalNeeded(adventure.getTotalNeeded());
        oldAdventure.setRequireApproval(adventure.getRequireApproval());
        oldAdventure.setRequiredGear(adventure.getRequiredGear());


        oldAdventure.setActivityType(adventure.getActivityType());
        oldAdventure.setIsLocationApproved(adventure.getIsLocationApproved());
        oldAdventure.setHasGovernmentPermit(adventure.getHasGovernmentPermit());



        validateCompliance(oldAdventure);

        adventureRepository.save(oldAdventure);
    }

    public void deleteAdventure(Integer id){
        Adventure adventure= adventureRepository.findAdventureById(id);
        if(adventure==null){
            throw new ApiException("Adventure not found");
        }
        adventureRepository.delete(adventure);
    }


    private void validateCompliance(Adventure adv) {
        String type = adv.getActivityType();
        if (type == null) throw new ApiException("activityType is required");

        switch (type.toUpperCase()) {
            case "RACING":
                if (!Boolean.TRUE.equals(adv.getIsLocationApproved())) {
                    throw new ApiException("RACING requires an approved track (isLocationApproved=true).");
                }
                break;
                case "DRIFTING":
                if (!Boolean.TRUE.equals(adv.getIsLocationApproved())) {
                    throw new ApiException("DRIFTING requires an approved track (isLocationApproved=true).");
                }

                break;
            case "HUNTING":
                if (!Boolean.TRUE.equals(adv.getHasGovernmentPermit())) {
                    throw new ApiException("HUNTING requires a government/environmental permit.");
                }

                break;
            default:

        }
    }



}

