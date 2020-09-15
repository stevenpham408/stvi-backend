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

    public boolean doesUsernameExist(User proposedUser){
        if(userRepo.findByUsername(proposedUser.getUsername()) != null){
            return true;
        }
        return false;
    }

    public boolean doesEmailExist(User proposedUser){
        if(userRepo.findByEmail(proposedUser.getEmail()) != null){
            return true;
        }
        return false;
    }
}