package com.interntest.cosmotechintl.service;

import com.interntest.cosmotechintl.entity.UserInfo;
import com.interntest.cosmotechintl.repository.UserInfoRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserService  {


    public Optional<UserInfo> findByEmail(String email);

    public boolean authenticateUser(String email, String rawPassword);

    public void registerUser(UserInfo userInfo);

    public Optional<UserInfo> findByUserName(String userName);
}
