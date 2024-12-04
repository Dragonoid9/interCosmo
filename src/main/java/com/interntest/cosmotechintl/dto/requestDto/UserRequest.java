package com.interntest.cosmotechintl.dto.requestDto;


import com.interntest.cosmotechintl.entity.UserRole;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String username;
    private String password;
    private List<String> roles;


}