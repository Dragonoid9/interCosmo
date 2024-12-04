package com.interntest.cosmotechintl.dto.requestDto;


import com.interntest.cosmotechintl.entity.UserRole;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private Set<UserRole> roles;


}