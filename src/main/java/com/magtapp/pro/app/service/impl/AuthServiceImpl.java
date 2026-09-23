package com.magtapp.pro.app.service.impl;

import com.magtapp.pro.app.dto.req.AppUserLogInRequest;
import com.magtapp.pro.app.dto.req.AppUserSignupRequest;
import com.magtapp.pro.app.dto.res.AppUserLogInResponse;
import com.magtapp.pro.app.dto.res.AppUserSignupResponse;
import com.magtapp.pro.app.entity.User;
import com.magtapp.pro.app.exception.DuplicateResourceException;
import com.magtapp.pro.app.exception.ResourceNotFoundException;
import com.magtapp.pro.app.repository.UserRepository;
import com.magtapp.pro.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.magtapp.pro.app.security.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AppUserSignupResponse signup(AppUserSignupRequest request) {
        // Check if the email already exists
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists " + request.email());
        }

        // need to create an app user after merchant is created and save it to the database
        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        user = userRepository.save(user);

        String token = jwtUtil.generateAccessToken(request.email(), user.getId());


        return new AppUserSignupResponse(user.getId(), request.email(), token);
    }

    @Override
    public AppUserLogInResponse login(AppUserLogInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email()).orElseThrow(() ->
                new ResourceNotFoundException("User not found:" + request.email())
        );

        String token = jwtUtil.generateAccessToken(request.email(), user.getId());

        return new AppUserLogInResponse(user.getId(), request.email(), token);
    }
}
