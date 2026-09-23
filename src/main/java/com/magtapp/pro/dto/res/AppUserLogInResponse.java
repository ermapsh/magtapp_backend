package com.magtapp.pro.dto.res;

import java.util.UUID;

public record AppUserLogInResponse(
        UUID id,
        String email,
        String accessToken
) {
}