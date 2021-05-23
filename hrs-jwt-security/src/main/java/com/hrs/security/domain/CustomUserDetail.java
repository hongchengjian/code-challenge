package com.hrs.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
public class CustomUserDetail implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    private String username;

    private long userId;

    private String password;

    private Boolean isDeleted;

    public CustomUserDetail(String username, List<? extends GrantedAuthority> authorityList) {
        this.username = username;
        this.authorities = authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;
    }
}
