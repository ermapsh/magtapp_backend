package com.magtapp.pro.app.dto.res;

import java.util.UUID;

public record AppUserLogInResponse(
        UUID id,
        String email,
        String accessToken
) {
}