package com.connect.web.util;

import com.connect.common.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthenticationUtil {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Authentication getAuthentication(String token) {
        String username = jwtTokenUtil.getUserIdFromToken(token);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(
                        jwtTokenUtil.getRolesFromToken(token)
                )
        );

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
