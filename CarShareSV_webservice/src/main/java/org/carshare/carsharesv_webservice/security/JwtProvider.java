package org.carshare.carsharesv_webservice.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;


/**
 * This class is used to validate the token and build the authentication object.
 */
@Component
public class JwtProvider {
    @Value("${app.jwt-secret}")
    private String secret; // secret key used to sign the JWT

    @Value("${app.jwt-expiration-time}")
    private String expirationTime; // time in milliseconds after which the JWT expires

    //Creates a JWT for the user
    public String generateToken(Authentication auth) {
        String username = auth.getName(); // get username from the authentication object
        Date now = new Date(); // current date and time
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expirationTime)); // calculate expiration date
        List<String> roles = auth.getAuthorities().stream().map(item -> item.getAuthority()).toList();

        // set the claims for the JWT, here we can add custom fields to the JWT, ex. roles, email, etc
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        // Build and sign the JWT
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret) // Decode the secret key from Base64
        );
    }

    // Extracts the username from the provided JWT token
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Extracts the roles from the provided JWT token
    public List<String> getRolesFromToken(String token) {
        try {
            Object rolesClaim = Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles");

            if (rolesClaim instanceof List<?> rolesList) {
                return rolesList.stream()
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .toList();
            }

            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Validates the provided JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser() // Parse the JWT token
                    .verifyWith((SecretKey) getKey()) // Use the secret key for validation
                    .build()
                    .parseSignedClaims(token); // Validate the token structure and signature
            return true; // Return true if the token is valid
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false; //not valid token
        }

    }
}
