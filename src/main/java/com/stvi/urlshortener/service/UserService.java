package com.stvi.urlshortener.service;

import com.stvi.urlshortener.entity.User;
import com.stvi.urlshortener.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepo;

    public User registerNewUser(User proposedUser){
        return userRepo.save(proposedUser);
    }

    public User getUserById(int id){
        return userRepo.findById(id);
    }

    public boolean doesUsernameExist(String username){
        if(userRepo.findByUsername(username) != null){
            return true;
        }
        return false;
    }

    public boolean doesEmailExist(String email){
        if(userRepo.findByEmail(email) != null){
            return true;
        }
        return false;
    }
}