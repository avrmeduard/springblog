package com.avrm.springblog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;

@Service
public class JwtProvider {

    private Key key;

    // we create at the time service start and we reuse the same key every time we generate a JSON token
    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // create a key json web token special algorithm
    }

    // takes the authenticate obj as parameter to generate the jwt token
    public String generateToken(Authentication authentication) {
        // we cast in the buildSecurity class
        User principal = (User) authentication.getPrincipal();

        // we invoke the builder method of the class
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwt) {
        // if the statement is execute whit any errors we can be sure is a valid token
        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        return true;
    }

    // we retrieve the body of the claim that we added
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(key)
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }
}
