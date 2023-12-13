package com.example.rgpdplatform.config.jwt;


import com.example.rgpdplatform.config.service.UserDetailsImpl;
import com.example.rgpdplatform.dto.auth.ExtendedUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {

    static final String issuer = "RGPD";

    @Value("${rgpd.saas.jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${rgpd.token.access.expirationDate}")
    private int EXPIRATION_DATE;

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String getEmailFromJwtToken(String token) {
        return extractData(token).get("email").toString();
    }

    public String generateAccessToken(ExtendedUser extendedUser) {
        return Jwts
                .builder()
                .claim("email", extendedUser.getUsername())
                .claim("role", extendedUser.getAuthorities().toArray()[0])
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

    }

    public String extractUsernameFromAuthorizationHeader(String token) {
        String tokenPostProcessing = token.substring(7);
        return extractData(tokenPostProcessing).get("email").toString();
    }

    public String extractRoleFromAuthorizationHeader(String token) {
        String tokenPostProcessing = token.substring(7);
        return extractData(tokenPostProcessing).get("role").toString();
    }

    private Claims extractData(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String subStringToken(String token) {
        return token.substring(7);
    }


    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public Boolean isTokenExpired(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}