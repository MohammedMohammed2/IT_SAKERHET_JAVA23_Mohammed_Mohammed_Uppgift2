package com.example.securitySpring.service;

import com.example.securitySpring.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    public String extractUser(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String email = extractUser(token);
        return (email.equals(user.getUsername()));
    }


    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

     private Claims extractAllClaims(String token){
         return Jwts
                 .parser()
                 .verifyWith(getSignInKey())
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();
     }
    private final String secretKey = "6942da8f5536a295f1bab67e4b78606d69c14bcda42c680f25090cde7e3f02c5";

    public String generateToken(User user){
         String token = Jwts
                 .builder()
                 .subject(user.getUsername())
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
                 .signWith(getSignInKey())
                 .compact();

         return  token;
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
