package com.hrs.security.service;

import com.hrs.security.domain.CustomUserDetail;

public interface CustomUserService {

    CustomUserDetail loadByUsername(String username);
}
