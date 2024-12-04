package com.interntest.cosmotechintl.service;


;
import com.interntest.cosmotechintl.dto.requestDto.UserRequest;
import com.interntest.cosmotechintl.dto.responseDto.UserResponse;
import com.interntest.cosmotechintl.entity.UserInfo;
import com.interntest.cosmotechintl.entity.UserRole;
import com.interntest.cosmotechintl.repository.UserRepository;
import com.interntest.cosmotechintl.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        // Encrypt password
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        // Map roles from UserRequest
        Set<UserRole> roles = userRequest.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        // Map UserRequest to UserInfo entity
        UserInfo userInfo = UserInfo.builder()
                .username(userRequest.getUsername())
                .password(encodedPassword)
                .roles(roles)
                .build();

        // Save user to database
        UserInfo savedUser = userRepository.save(userInfo);

        // Map saved user to UserResponse DTO
        return mapToUserResponse(savedUser);
    }

//    @Override
//    public UserResponse getUserById(Long id) {
//        UserInfo userInfo = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
//
//        return mapToUserResponse(userInfo);
//    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserInfo> users = userRepository.findAll();

        // Map each user to UserResponse
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // Helper method to map UserInfo entity to UserResponse DTO
    private UserResponse mapToUserResponse(UserInfo userInfo) {
        return UserResponse.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .roles(userInfo.getRoles())
                .build();
    }
}