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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller class that processes incoming requests for the URL Shortener web application
 * @author stevenpham408
 */
@SuppressWarnings("SameReturnValue")
@RestController
//@Controller
@RequiredArgsConstructor
public class    WebApplicationController {
    private final PasswordEncoder passwordEncoder;
    private final UserAccountService userService;
    private final ShortUrlService urlService;

    @GetMapping("/")
    @ResponseBody
    public String def(){
        if(!isAuthenticated()){
            System.out.println("Resource not found!");
            throw new ResourceNotFoundException();
        }
        return "Default Mapping Accessed";
    }
    
    @GetMapping("/user/url")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public Page<ShortUrl> getUserAccount(Pageable pageable){
        if(!isAuthenticated()){
            System.out.println("Resource not found! (/user/url)");
            throw new ResourceNotFoundException();
        }

        System.out.println("Authenticated!");
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authUserAccount = ((CustomUserDetails) authUser.getPrincipal());
        return urlService.getAllShortUrlFromUserId(authUserAccount.getId(), pageable);
    }

    @PostMapping("/registration")
    @CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/url")
    @ResponseBody
    public ShortUrl createShortUrl(@RequestBody UrlDto urlDto){
        if(!isAuthenticated()){
            System.out.println("Exception");
            throw new ResourceNotFoundException();
        }

        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authUserAccount = ((CustomUserDetails) authUser.getPrincipal());

        if(urlService.doesLongUrlExistForUser(urlDto.getLongUrl(), authUserAccount.getId())){
            return null;
        }

        System.out.println("Not duplicate, current user: " + (urlDto.getLongUrl() + authUserAccount.getId()));
        return urlService.makeShortUrl(urlDto.getLongUrl(), authUserAccount.getId());
    }

    @GetMapping("/url/{hash}")
    @ResponseBody
    public String getRedirect(@PathVariable String hash){
        if(!urlService.doesShortUrlExist(hash)){
            System.out.println("Resource not found!");
            throw new ResourceNotFoundException();
        }
        return urlService.getLongUrlByHash(hash);
    }

    @GetMapping("/user/auth")
    @ResponseBody
    public boolean getAuth(){
        if(!isAuthenticated()){
            return false;
        }
        System.out.println("User is authenticated");
        return true;
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