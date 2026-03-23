package com.yoonsunmi.flashCoupon.domain.auth.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetail implements UserDetails {

    private final AppUser user;

    public UserDetail(AppUser user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getLoginId() {
        return user.getLoginId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + user.getRole());
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }
}
