package com.stvi.urlshortener.controller;

import com.stvi.urlshortener.controller.exceptions.EmailAlreadyExistsException;
import com.stvi.urlshortener.controller.exceptions.UsernameAlreadyExistsException;
import com.stvi.urlshortener.dto.UserDto;
import com.stvi.urlshortener.entity.User;
import com.stvi.urlshortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private final PasswordEncoder passwordEncoder;
    private UserService userService;
    @GetMapping("/login")
    public String text(){
        return "Hello World";
    }

    @GetMapping("/user/{id}")
    public User getUserAccount(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping("/registration")
    public User createUserAccount(@RequestBody @Valid UserDto proposedUser){
        if(userService.doesUsernameExist(proposedUser.getUsername())){
            throw new UsernameAlreadyExistsException();
        }

        if(userService.doesEmailExist(proposedUser.getEmail())){
            throw new EmailAlreadyExistsException();
        }

        User newUser = new User();
        newUser.setUsername(proposedUser.getUsername());
        newUser.setEmail(proposedUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(proposedUser.getPassword()));
        return userService.registerNewUser(newUser);
    }
}