package com.stvi.urlshortener.controller;

import com.stvi.urlshortener.entity.User;
import com.stvi.urlshortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private UserService userService;
    @PostMapping("/registration")
    public User createUserAccount(@RequestBody @Valid User proposedUser){
        return userService.registerNewUser(proposedUser);
    }
}