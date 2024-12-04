package com.interntest.cosmotechintl.dto.responseDto;

import com.interntest.cosmotechintl.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private Set<UserRole> roles;


}
