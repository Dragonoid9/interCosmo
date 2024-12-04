package com.interntest.cosmotechintl.dto.requestDto;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    private String username;
    private String password;
}
