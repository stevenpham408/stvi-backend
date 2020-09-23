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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller class that processes incoming requests for the URL Shortener web application
 * @author stevenpham408
 */
//@RestController
@SuppressWarnings("SameReturnValue")
@RestController
@RequiredArgsConstructor
public class    WebApplicationController {
    private final PasswordEncoder passwordEncoder;
    private final UserAccountService userService;
    private final ShortUrlService urlService;

    /* Because we are using Spring Security to handle our security provisions, we define the PostMapping for /login
     * inside of ApplicationSecurityConfig.java
     */


    @GetMapping("/user/{id}")
    public UserAccount getUserAccount(@PathVariable int id){
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authUserAccount = ((CustomUserDetails) authUser.getPrincipal());

        if(authUserAccount.getId() != id){
            throw new UserNotAuthorizedException();
        }
        return userService.getUserById(id);
    }

    @PostMapping("/registration")
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

    @PostMapping("/url")
    public ShortUrl createShortUrl(@RequestBody UrlDto urlDto){
        // Check service if the URL has already been shortened by the user
        if(urlService.doesLongUrlExist(urlDto.getLongUrl())){
            System.out.println("Duplicate request found.");
            return urlService.getShortUrlByLongUrl(urlDto.getLongUrl());
        }

        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authUserAccount = ((CustomUserDetails) authUser.getPrincipal());

        return urlService.makeShortUrl(urlDto.getLongUrl(), authUserAccount.getId());
    }

    @GetMapping("/url/{hash}")
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