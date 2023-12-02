package com.connect.data.entity;

import com.connect.common.enums.Role;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Accessors(chain = true)
@Data
public class UserSecurity implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private int role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(
                        Role.getRole(role)
                )
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        // You can implement logic here to check if the user account is non-expired
        // For example, return true if the account is not expired, or false otherwise
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // You can implement logic here to check if the user account is non-expired
        // For example, return true if the account is not expired, or false otherwise
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
