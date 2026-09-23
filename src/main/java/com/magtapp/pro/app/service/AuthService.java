package com.magtapp.pro.app.service;

import com.magtapp.pro.app.dto.req.AppUserLogInRequest;
import com.magtapp.pro.app.dto.req.AppUserSignupRequest;
import com.magtapp.pro.app.dto.res.AppUserLogInResponse;
import com.magtapp.pro.app.dto.res.AppUserSignupResponse;

public interface AuthService {
    AppUserSignupResponse signup(AppUserSignupRequest request);
    AppUserLogInResponse login(AppUserLogInRequest request);
}
