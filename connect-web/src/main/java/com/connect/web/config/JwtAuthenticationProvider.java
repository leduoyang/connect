package com.connect.web.config;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.user.iml.UserSecurityServiceImpl;
import com.connect.web.util.JwtAuthenticationUtil;
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

    @Autowired
    private JwtAuthenticationUtil jwtAuthenticationUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userSecurityService.loadUserByUsername(authentication.getPrincipal().toString());
        if (userDetails == null) {
            throw new ConnectDataException(ConnectErrorCode.INTERNAL_SERVER_ERROR, "jwt authenticate failed");
        }

        log.info("load user details for " + userDetails.getUsername());
        if (!authentication.getPrincipal().equals(userDetails.getUsername())) {
            throw new ConnectDataException(ConnectErrorCode.INTERNAL_SERVER_ERROR, "roles authenticate failed");
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

