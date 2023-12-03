package com.connect.web.config;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.web.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            return authenticationUtil.createAuthentication(
                    authentication.getCredentials().toString()
            );
        } catch (Exception e) {
            throw new ConnectDataException(ConnectErrorCode.INTERNAL_SERVER_ERROR, "jwt authenticate failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

