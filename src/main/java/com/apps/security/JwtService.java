package com.apps.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt-expire-time}")
    private Integer expireTime;

    @Value("${jwt-key}")
    private String key;

    public String createJwtToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expireTime)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean isValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.warn(String.valueOf(e));
        }
        return false;
    }
}
