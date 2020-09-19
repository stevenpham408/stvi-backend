package com.stvi.urlshortener.security;

import com.stvi.urlshortener.entity.UserAccount;
import com.stvi.urlshortener.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " not found");
        }

        return CustomUserDetails.buildCustomUserDetails()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .grantedAuthorities(UserRole.USER.getGrantedAuthorities())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }
}