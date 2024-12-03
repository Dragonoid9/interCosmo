package com.interntest.cosmotechintl.controller;


import com.interntest.cosmotechintl.dto.requestDto.UserRequest;
import com.interntest.cosmotechintl.entity.UserInfo;
import com.interntest.cosmotechintl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {

        if (userService.findByEmail(userRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }

        if (userService.findByUserName(userRequest.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }


        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userRequest.getEmail());
        userInfo.setPassword(userRequest.getPassword());
        userInfo.setUserName(userRequest.getUserName());
        userInfo.setRoles("ROLE_USER");
        userService.registerUser(userInfo);
        return ResponseEntity.ok("User registered successfully.");
    }
}
