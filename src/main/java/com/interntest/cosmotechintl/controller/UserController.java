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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }

    @PostMapping("/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
