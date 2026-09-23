package com.magtapp.pro.service;

import com.magtapp.pro.dto.req.AppUserLogInRequest;
import com.magtapp.pro.dto.req.AppUserSignupRequest;
import com.magtapp.pro.dto.res.AppUserLogInResponse;
import com.magtapp.pro.dto.res.AppUserSignupResponse;

public interface AuthService {
    AppUserSignupResponse signup(AppUserSignupRequest request);
    AppUserLogInResponse login(AppUserLogInRequest request);
}
