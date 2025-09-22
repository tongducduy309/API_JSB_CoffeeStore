package com.api_coffee_store.api_coffee_store.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;

}
