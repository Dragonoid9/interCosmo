package com.interntest.cosmotechintl.controller;


import com.interntest.cosmotechintl.config.JwtGenerator;
import com.interntest.cosmotechintl.dto.LoginRequest;
import com.interntest.cosmotechintl.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtGenerator jwtGenerator;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest){
        var token = jwtGenerator.createToken(1L, loginRequest.getEmail(), List.of("USER"));
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
