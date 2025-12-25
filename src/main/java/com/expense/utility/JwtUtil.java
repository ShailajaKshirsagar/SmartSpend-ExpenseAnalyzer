package com.expense.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY ="expense_analyzer_secret_1234567890";

    public String generateToken(Long userId, String email){
        return Jwts.builder()
                .setSubject(email)
                .claim("userId",userId)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + Duration.ofDays(60).toMillis())  // 2months expiration
                ).signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey((SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId",Long.class);
    }
}
