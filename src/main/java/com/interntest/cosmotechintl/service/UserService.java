package com.interntest.cosmotechintl.service;



import com.interntest.cosmotechintl.dto.requestDto.RoleRequest;
import com.interntest.cosmotechintl.dto.requestDto.UserRequest;
import com.interntest.cosmotechintl.dto.responseDto.UserResponse;
import com.interntest.cosmotechintl.entity.UserRole;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);

    List<UserResponse> getAllUser();

    void deleteRoleById(Long roleId);

    UserRole addRole(RoleRequest roleRequest);

}
