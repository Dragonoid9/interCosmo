package com.interntest.cosmotechintl.controller;


import com.interntest.cosmotechintl.config.JwtGenerator;
import com.interntest.cosmotechintl.config.UserPrincipal;
import com.interntest.cosmotechintl.dto.requestDto.LoginRequest;
import com.interntest.cosmotechintl.dto.responseDto.LoginResponse;
import com.interntest.cosmotechintl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtGenerator jwtGenerator;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest){

            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal =(UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

            var token = jwtGenerator.createToken(principal.getUserId(), principal.getEmail(),principal.getUsername(), roles);

            return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
