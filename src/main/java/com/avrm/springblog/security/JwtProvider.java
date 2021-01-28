package com.avrm.springblog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    // takes the authenticate obj as parameter to generate the jwt token
    public String generateToken(Authentication authentication) {
        // we cast in the buildSecurity class
        User principal = (User) authentication.getPrincipal();

        // we invoke the builder method of the class
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512)) // create a key json web token special algorithm
                .compact();
    }
}
