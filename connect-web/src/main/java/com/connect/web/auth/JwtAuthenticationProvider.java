package com.connect.web.auth;

import com.connect.common.enums.UserRole;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.user.impl.UserSecurityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserSecurityServiceImpl userSecurityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication.getPrincipal().toString().equals(UserRole.ROOT.toString())) {
            log.info("testing token held by root entering");
            return authentication;
        }

        UserDetails userDetails = userSecurityService.loadUserByUsername(authentication.getName());
        if (userDetails == null) {
            throw new ConnectDataException(ConnectErrorCode.INTERNAL_SERVER_ERROR, "jwt authenticate failed");
        }

        log.info("load user details for " + userDetails.getUsername());
        if (!userDetails.isAccountNonLocked()) {
            throw new ConnectDataException(ConnectErrorCode.INTERNAL_SERVER_ERROR, "user has been DELETED and LOCKED");
        }
        if (!authentication.getAuthorities().toString().equals(userDetails.getAuthorities().toString())) {
            throw new ConnectDataException(ConnectErrorCode.INTERNAL_SERVER_ERROR, "roles authenticate failed");
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

