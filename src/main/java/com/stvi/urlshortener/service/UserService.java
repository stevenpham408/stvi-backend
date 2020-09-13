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

}
