package com.interntest.cosmotechintl.repository;


import com.interntest.cosmotechintl.entity.RefreshToken;
import com.interntest.cosmotechintl.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByUserInfo(UserInfo byUsername);
}
