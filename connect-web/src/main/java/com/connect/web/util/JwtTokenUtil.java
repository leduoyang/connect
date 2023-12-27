package com.connect.web.util;

import com.connect.common.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil {
    public final String PREFIX = "Bearer ";

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration:864000000}")
    private long EXPIRATION_TIME; // 10 days in milliseconds

    public String generateToken(String userId) {
        String role = UserRole.ESSENTIAL.toString();
        return generateToken(userId, role);
    }

    public String generateToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", role);
        claims.put("created", new Date());

        log.info("generating token for " + claims);
        log.info("expiration time : " + EXPIRATION_TIME);
        return PREFIX + Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token.replaceFirst("^" + PREFIX, ""));
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replaceFirst("^" + PREFIX, ""))
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }
}
