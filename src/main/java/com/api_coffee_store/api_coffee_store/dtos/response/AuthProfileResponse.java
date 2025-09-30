package com.api_coffee_store.api_coffee_store.dtos.response;

import java.time.LocalDateTime;

public record AuthProfileResponse(
        String id,
        String fullname,
        String email
) {
}
