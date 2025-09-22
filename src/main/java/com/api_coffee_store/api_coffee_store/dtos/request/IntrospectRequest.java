package com.api_coffee_store.api_coffee_store.dtos.request;

public class IntrospectRequest {
    String token;

    public IntrospectRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
