package com.magtapp.pro.app.controller;


import com.magtapp.pro.app.dto.req.AppUserLogInRequest;
import com.magtapp.pro.app.dto.req.AppUserSignupRequest;
import com.magtapp.pro.app.dto.res.AppUserLogInResponse;
import com.magtapp.pro.app.dto.res.AppUserSignupResponse;
import com.magtapp.pro.common.dto.res.ApiResponse;
import com.magtapp.pro.app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<AppUserSignupResponse>> signup(@RequestBody @Valid AppUserSignupRequest request) {
        AppUserSignupResponse response = authService.signup(request);
        return ApiResponse.created(
                "App user created successfully",
                response
        );
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AppUserLogInResponse>> login(@RequestBody @Valid AppUserLogInRequest request) {
        AppUserLogInResponse response = authService.login(request);
        return ApiResponse.created(
                "App user fetch successfully",
                response
        );
    }

}
