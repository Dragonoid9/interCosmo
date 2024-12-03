package com.interntest.cosmotechintl.controller;

import com.interntest.cosmotechintl.config.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "You are secured!: UserId: "+userPrincipal.getUserId()
                +"  Username: "+userPrincipal.getUsername()
        +"User email:"+userPrincipal.getEmail()
                +"Role is :"+userPrincipal.getAuthorities();
    }
}
