package com.connect.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Component
public class AuthenticationUtil {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Authentication createAuthentication(String token) {
        String username = jwtTokenUtil.getUserIdFromToken(token);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(
                        jwtTokenUtil.getRoleFromToken(token)
                )
        );

        return new UsernamePasswordAuthenticationToken(username, token, authorities);
    }
}
