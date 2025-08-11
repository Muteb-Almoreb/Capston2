package com.example.capstone2_adventureandoutdoorexperience.Repository;

import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);


}
