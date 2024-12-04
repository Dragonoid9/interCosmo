package com.interntest.cosmotechintl.controller;

import com.interntest.cosmotechintl.dto.requestDto.AuthRequestDto;
import com.interntest.cosmotechintl.dto.requestDto.RefreshTokenRequestDto;
import com.interntest.cosmotechintl.dto.requestDto.UserRequest;
import com.interntest.cosmotechintl.dto.responseDto.JwtResponseDto;
import com.interntest.cosmotechintl.dto.responseDto.UserResponse;
import com.interntest.cosmotechintl.entity.RefreshToken;
import com.interntest.cosmotechintl.service.JwtService;
import com.interntest.cosmotechintl.service.RefreshTokenService;
import com.interntest.cosmotechintl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage(), e);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<JwtResponseDto> authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            JwtResponseDto jwtResponse = JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
            return ResponseEntity.ok(jwtResponse);
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @PostMapping("/refreshToken")
    @ResponseBody
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    JwtResponseDto jwtResponse = JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken())
                            .build();
                    return ResponseEntity.ok(jwtResponse);
                })
                .orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));
    }
}
