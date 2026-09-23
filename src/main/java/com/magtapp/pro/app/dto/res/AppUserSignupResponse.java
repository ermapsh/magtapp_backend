package com.magtapp.pro.app.dto.res;
import java.util.UUID;

public record AppUserSignupResponse(
        UUID id,
        String email,
        String accessToken
) {
}
