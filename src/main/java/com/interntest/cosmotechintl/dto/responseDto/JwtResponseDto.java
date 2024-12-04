package com.interntest.cosmotechintl.dto.responseDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {

    private String accessToken;
    private String token;
}
