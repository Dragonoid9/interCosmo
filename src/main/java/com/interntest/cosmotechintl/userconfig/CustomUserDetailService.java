package com.interntest.cosmotechintl.userconfig;

import com.interntest.cosmotechintl.config.UserPrincipal;
import com.interntest.cosmotechintl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user =userService.findByEmail(username).orElseThrow();

        return UserPrincipal.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRoles())))
                .password(user.getPassword())
                .build();
    }
}
