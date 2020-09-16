package com.stvi.urlshortener.controller;

import com.stvi.urlshortener.controller.exceptions.EmailAlreadyExistsException;
import com.stvi.urlshortener.controller.exceptions.UsernameAlreadyExistsException;
import com.stvi.urlshortener.dto.UserDto;
import com.stvi.urlshortener.entity.UserAccount;
import com.stvi.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

//@RestController
@SuppressWarnings("SameReturnValue")
@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    @ResponseBody
    public String def() { return "Welcome to the Home Page!"; }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/success")
    public String successLogin() { return "LoginSuccess";}

    @GetMapping("/user/{id}")
    public UserAccount getUserAccount(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping("/registration")
    public UserAccount createUserAccount(@RequestBody @Valid UserDto proposedUser){
        if(userService.doesUsernameExist(proposedUser.getUsername())){
            throw new UsernameAlreadyExistsException();
        }

        if(userService.doesEmailExist(proposedUser.getEmail())){
            throw new EmailAlreadyExistsException();
        }

        // NOTE2SELF: Look into using Builder from Lombok to replace this block
        UserAccount newUser = new UserAccount();
        newUser.setUsername(proposedUser.getUsername());
        newUser.setEmail(proposedUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(proposedUser.getPassword()));
        return userService.registerNewUser(newUser);
    }
}