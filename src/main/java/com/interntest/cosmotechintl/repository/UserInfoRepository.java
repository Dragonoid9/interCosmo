package com.interntest.cosmotechintl.repository;

import com.interntest.cosmotechintl.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {


    UserInfo findByEmail(String email);
}
