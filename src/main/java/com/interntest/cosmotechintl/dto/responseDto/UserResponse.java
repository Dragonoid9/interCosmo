package com.interntest.cosmotechintl.dto.responseDto;

import com.interntest.cosmotechintl.entity.UserRole;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private Set<UserRole> roles;


}
