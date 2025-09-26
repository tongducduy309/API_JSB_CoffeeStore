package com.api_coffee_store.api_coffee_store.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthTokenRequest {
    @NotBlank String token;
}
