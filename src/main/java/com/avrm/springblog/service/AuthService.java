package com.avrm.springblog.service;

import com.avrm.springblog.dto.LoginRequest;
import com.avrm.springblog.dto.RegisterRequest;
import com.avrm.springblog.model.User;
import com.avrm.springblog.repository.UserRepository;
import com.avrm.springblog.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    // it will use our implementation service class
    @Autowired
    private UserRepository userRepository;

    // create a new instance of b-crypt password to secure the passwords
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());

        userRepository.save(user);
    }

    // we create a method to hash the password and save it to db
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String logIn(LoginRequest loginRequest) {
        // provide our user credentials by wrapping them up inside a class 'UsernamePasswordAuthenticationToken'
        // once the user pass this method we cand be sure is authenticated
        Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        // with that we implemented the authentication process
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // generate jwt token
        return jwtProvider.generateToken(authenticate);
    }



}
