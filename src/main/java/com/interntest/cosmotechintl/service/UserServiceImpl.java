package com.interntest.cosmotechintl.service;


;
import com.interntest.cosmotechintl.dto.requestDto.UserRequest;
import com.interntest.cosmotechintl.dto.responseDto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse getUser() {
        return null;
    }

    @Override
    public List<UserResponse> getAllUser() {
        return List.of();
    }
}
