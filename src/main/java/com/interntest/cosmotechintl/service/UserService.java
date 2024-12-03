package com.interntest.cosmotechintl.service;


import com.interntest.cosmotechintl.entity.UserInfo;
import com.interntest.cosmotechintl.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserInfoRepository userInfoRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserInfo> findByEmail(String email) {
        return Optional.ofNullable(userInfoRepository.findByEmail(email));
    }

    public boolean authenticateUser(String email, String rawPassword) {
        return findByEmail(email)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    public UserInfo registerUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }
}
