package com.interntest.cosmotechintl.dto.requestDto;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDto {
    private String token;
}
