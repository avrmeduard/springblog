package com.avrm.springblog.controller;


import com.avrm.springblog.dto.LoginRequest;
import com.avrm.springblog.dto.RegisterRequest;
import com.avrm.springblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody RegisterRequest registerRequest) {

        authService.signUp(registerRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody LoginRequest loginRequest) {
        authService.logIn(loginRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
