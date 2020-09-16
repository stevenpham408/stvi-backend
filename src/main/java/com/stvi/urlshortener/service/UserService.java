package com.stvi.urlshortener.service;

import com.stvi.urlshortener.entity.UserAccount;
import com.stvi.urlshortener.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserAccountRepository userRepo;

    public UserAccount registerNewUser(UserAccount proposedUserAccount){
        return userRepo.save(proposedUserAccount);
    }

    public UserAccount getUserById(int id){
        return userRepo.findById(id);
    }

    public boolean doesUsernameExist(String username){
        return userRepo.findByUsername(username) != null;
    }

    public boolean doesEmailExist(String email){
        return userRepo.findByEmail(email) != null;
    }
}