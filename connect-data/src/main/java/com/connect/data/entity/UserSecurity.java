package com.connect.data.entity;

import com.connect.common.enums.UserRole;
import com.connect.common.enums.UserStatus;
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

    private int status;

    private int role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(
                        UserRole.getRole(role)
                )
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        if (status == UserStatus.DELETED.getCode()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (status == UserStatus.DELETED.getCode()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // This method checks whether the user's credentials (e.g., password) have expired.
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
