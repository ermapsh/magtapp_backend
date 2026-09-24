package com.magtapp.pro.app.controller;

import com.magtapp.pro.app.dto.res.UserProfileResponse;
import com.magtapp.pro.app.security.UserContext;
import com.magtapp.pro.app.service.UserService;
import com.magtapp.pro.common.dto.res.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserContext userContext;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        UserProfileResponse response = userService.getProfile(userContext.getUserId());
        return ApiResponse.created(
                "User fetch successfully",
                response
        );
    }
}
