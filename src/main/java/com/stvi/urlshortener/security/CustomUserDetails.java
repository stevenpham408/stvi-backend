package com.stvi.urlshortener.security;

import com.stvi.urlshortener.entity.UserAccount;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public class CustomUserDetails extends UserAccount implements UserDetails {
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    @Builder(builderMethodName="buildCustomUserDetails")
    public CustomUserDetails(int id, String username, String password, String email,
                             Set<? extends  GrantedAuthority> grantedAuthorities,
                             boolean isAccountNonExpired, boolean isAccountNonLocked,
                             boolean isCredentialsNonExpired, boolean isEnabled){
        super(id, username, password, email);
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities   ;
    }
}
