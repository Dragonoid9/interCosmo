package com.interntest.cosmotechintl.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT decodedJWT) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(decodedJWT.getSubject()))
                .email(decodedJWT.getClaim("e").asString())
                .authorities(extractAuthorities(decodedJWT))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthorities(DecodedJWT decodedJWT) {
        var claim = decodedJWT.getClaim("r");
        if(claim.isNull() || !claim.isMissing())
            return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
