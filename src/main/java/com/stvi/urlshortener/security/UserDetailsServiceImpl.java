package com.stvi.urlshortener.security;

import com.stvi.urlshortener.entity.UserAccount;
import com.stvi.urlshortener.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount user = userRepo.findByUsername(username);
        if(user == null){
            System.out.println("THROW ERROR");
            throw new UsernameNotFoundException(username + " not found");
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
