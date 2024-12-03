package com.interntest.cosmotechintl.controller;


import com.interntest.cosmotechintl.dto.requestDto.LoginRequest;
import com.interntest.cosmotechintl.dto.responseDto.LoginResponse;
import com.interntest.cosmotechintl.service.AuthService;
import com.interntest.cosmotechintl.service.AuthServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest){
    return authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
