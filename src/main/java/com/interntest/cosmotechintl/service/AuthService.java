package com.interntest.cosmotechintl.service;

import com.interntest.cosmotechintl.dto.responseDto.LoginResponse;

public interface AuthService {

     LoginResponse authenticate (String email, String password);
}
