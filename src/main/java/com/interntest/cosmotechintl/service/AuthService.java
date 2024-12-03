package com.interntest.cosmotechintl.service;


import com.interntest.cosmotechintl.config.JwtGenerator;
import com.interntest.cosmotechintl.config.UserPrincipal;
import com.interntest.cosmotechintl.dto.responseDto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;

    public LoginResponse authenticate (String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
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
