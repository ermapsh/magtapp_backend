package com.magtapp.pro.app.service;

import com.magtapp.pro.app.dto.res.UserProfileResponse;

import java.util.UUID;

public interface UserService {
    UserProfileResponse getProfile(UUID userId);
}
