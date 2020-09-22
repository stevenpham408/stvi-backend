package com.stvi.urlshortener.controller;

import com.stvi.urlshortener.controller.exceptions.EmailAlreadyExistsException;
import com.stvi.urlshortener.controller.exceptions.ResourceNotFoundException;
import com.stvi.urlshortener.controller.exceptions.UserNotAuthorizedException;
import com.stvi.urlshortener.controller.exceptions.UsernameAlreadyExistsException;
import com.stvi.urlshortener.dto.UrlDto;
import com.stvi.urlshortener.dto.UserDto;
import com.stvi.urlshortener.entity.ShortUrl;
import com.stvi.urlshortener.entity.UserAccount;
import com.stvi.urlshortener.security.CustomUserDetails;
import com.stvi.urlshortener.service.ShortUrlService;
import com.stvi.urlshortener.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

//@RestController
@SuppressWarnings("SameReturnValue")
@Controller
@RequiredArgsConstructor
public class    WebApplicationController {
    private final PasswordEncoder passwordEncoder;
    private final UserAccountService userService;
    private final ShortUrlService urlService;

    @GetMapping("/")
    @ResponseBody
    public String home() { return "Welcome to the home page. "; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @DeleteMapping("/logout")
    public String logout() { return "You have successfully logged out."; }

    @GetMapping("/success")
    public String successLogin() {
        return "LoginSuccess";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public UserAccount getUserAccount(@PathVariable int id){
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authUserAccount = ((CustomUserDetails) authUser.getPrincipal());
        if(authUserAccount.getId() != id){
            throw new UserNotAuthorizedException();
        }

        return userService.getUserById(id);
    }

    @PostMapping("/registration")
    @ResponseBody
    public UserAccount createUserAccount(@RequestBody @Valid UserDto proposedUser){
        // Username already exists in the database
        if(userService.doesUsernameExist(proposedUser.getUsername())){
            throw new UsernameAlreadyExistsException();
        }

        // Email already exists in the database
        if(userService.doesEmailExist(proposedUser.getEmail())){
            throw new EmailAlreadyExistsException();
        }

        // User is already logged into a registered account
        if(isAuthenticated()){
            throw new UserNotAuthorizedException();
        }

        // NOTE2SELF: Look into using Builder from Lombok to replace this block
        UserAccount newUser = new UserAccount(proposedUser.getUsername(),
                passwordEncoder.encode(proposedUser.getPassword()), proposedUser.getEmail());

        return userService.registerNewUser(newUser);
    }

    @PostMapping("/api/url")
    @ResponseBody
    public ShortUrl createShortUrl(@RequestBody UrlDto urlDto){
        // Check service if the URL has already been shortened by the user
        if(urlService.doesLongUrlExist(urlDto.getLongUrl())){
            System.out.println("Duplicate request found.");
            return urlService.getShortUrlByLongUrl(urlDto.getLongUrl());
        }

        return urlService.makeShortUrl(urlDto.getLongUrl());
    }

    @GetMapping("/{hash}")
    @ResponseBody
    public String getRedirect(@PathVariable String hash){
        if(!urlService.doesShortUrlExist(hash)){
            throw new ResourceNotFoundException();
        }

        return urlService.getLongUrlByHash(hash);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}