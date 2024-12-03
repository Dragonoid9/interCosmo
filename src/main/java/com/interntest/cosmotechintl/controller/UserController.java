package com.interntest.cosmotechintl.controller;


import com.interntest.cosmotechintl.dto.requestDto.UserRequest;
import com.interntest.cosmotechintl.entity.UserInfo;
import com.interntest.cosmotechintl.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public UserInfo registerUser(@RequestBody UserRequest userRequest) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userRequest.getEmail());
        userInfo.setPassword(userRequest.getPassword());
        userInfo.setUserName(userRequest.getUserName());
        userInfo.setRoles("ROLE_USER");
        return userService.registerUser(userInfo);
    }
}
