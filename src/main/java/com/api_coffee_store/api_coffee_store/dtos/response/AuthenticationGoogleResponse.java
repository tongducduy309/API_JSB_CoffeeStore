package com.api_coffee_store.api_coffee_store.dtos.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuthenticationGoogleResponse(
        String id,
        String fullname,

        String token
) {
}
