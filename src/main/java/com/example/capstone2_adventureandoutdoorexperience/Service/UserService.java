package com.example.capstone2_adventureandoutdoorexperience.Service;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiException;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void addUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public void updateUser(Integer id, User user) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            throw new ApiException("The User Not found");
        }
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        oldUser.setGender(user.getGender());
        oldUser.setDateOfBirth(user.getDateOfBirth());
        oldUser.setHasOwnEquipment(user.getHasOwnEquipment());

        userRepository.save(oldUser);
    }

    public void delete(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }


    public User getUserById(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) throw new ApiException("User not found");
        return user;
    }
}