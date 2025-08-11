package com.example.capstone2_adventureandoutdoorexperience.Service;


import com.example.capstone2_adventureandoutdoorexperience.API.ApiException;
import com.example.capstone2_adventureandoutdoorexperience.Model.Adventure;
import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRating;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRatingRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRequestRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdventureRatingService {


    private final AdventureRatingRepository adventureRatingRepository;
    private final AdventureRepository adventureRepository;
    private final AdventureRequestRepository adventureRequestRepository;
    private final UserRepository userRepository;


    public List<AdventureRating> getByAdventure(Integer adventureId){
        return adventureRatingRepository.findByAdventureId(adventureId);
    }

    public void add(AdventureRating rating){
        if (rating.getAdventureId() == null)
            throw new ApiException("Adventure ID is required");

        if (rating.getUserId() == null)
            throw new ApiException("User ID is required");

        if (rating.getScore() == null)
            throw new ApiException("Score is required");

        if (rating.getScore() < 1 || rating.getScore() > 5)
            throw new ApiException("Score must be 1..5");

        Adventure adv = adventureRepository.findAdventureById(rating.getAdventureId());
        if (adv == null) throw new ApiException("Adventure not found");


        User user = userRepository.findUserById(rating.getUserId());
        if (user == null) throw new ApiException("User not found");

        boolean attended = adventureRequestRepository
                .existsByUserIdAndAdventureIdAndStatusIgnoreCase(rating.getUserId(), rating.getAdventureId(), "ATTENDED");
        if (!attended)
            throw new ApiException("Only attendees can rate this adventure");

        boolean exists = adventureRatingRepository.existsByAdventureIdAndUserId(rating.getAdventureId(), rating.getUserId());
        if (exists) throw new ApiException("You already rated this adventure");

        if (rating.getCreatedAt() == null)
            rating.setCreatedAt(LocalDateTime.now());

        adventureRatingRepository.save(rating);
    }






    public void update(Integer id, AdventureRating rating){
        AdventureRating oldRating = adventureRatingRepository.findAdventureRatingById(id);
        if (oldRating == null) throw new ApiException("Rating not found");

        if (rating.getUserId() == null) throw new ApiException("User ID is required");
        if (!oldRating.getUserId().equals(rating.getUserId())) throw new ApiException("You can only update your rating");

        if (rating.getScore() == null) throw new ApiException("Score is required");
        if (rating.getScore() < 1 || rating.getScore() > 5) throw new ApiException("Score must be 1..5");

        oldRating.setScore(rating.getScore());
        oldRating.setReview(rating.getReview());

        adventureRatingRepository.save(oldRating);
    }





    public void delete(Integer id){
        AdventureRating rating = adventureRatingRepository.findAdventureRatingById(id);
        if (rating == null)
            throw new ApiException("Rating not found");

        adventureRatingRepository.delete(rating);
    }



    public Double getAverage(Integer adventureId) {
        Double avg = adventureRatingRepository.avgScore(adventureId);
        if (avg == null) {
            avg = 0.0;
        }
        return avg;
    }

}
