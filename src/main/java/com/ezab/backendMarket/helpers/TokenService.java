package com.ezab.backendMarket.helpers;

import com.ezab.backendMarket.entities.User;
import com.ezab.backendMarket.exceptions.ValidateServiceException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Slf4j
public class TokenService {

    @Value(value = "${jwt.password}")
    private String jwtSecret;

    public String createToken(User user) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + (1000 * 60)*60);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected by the application.");
        } catch (MalformedJwtException e) {
            log.error("JWT was not correctly constructed and should be rejected.");
        } catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed.");
        } catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected.");
        }
        return false;
    }

    public String getUsernameFromToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidateServiceException("Invalid token");
        }

    }
}
