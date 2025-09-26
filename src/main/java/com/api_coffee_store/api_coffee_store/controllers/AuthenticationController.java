package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.AuthTokenRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.AuthenticationRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.IntrospectRequest;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.AuthencationService;
import com.api_coffee_store.api_coffee_store.services.GoogleAuthService;
import com.api_coffee_store.api_coffee_store.services.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthencationService authencationService;
    private final GoogleAuthService googleAuthService;

    @PostMapping("/google")
    public ResponseEntity<ResponseObject> loginGoogle(@RequestBody AuthTokenRequest req) {
        return googleAuthService.loginWithGoogleIdToken(req.getToken());
    }
    @PostMapping("/authenticate")
    ResponseEntity<ResponseObject> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws APIException {
        return authencationService.authenticate(authenticationRequest);
    }
    @PostMapping("/introspect")
    ResponseEntity<ResponseObject> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return authencationService.introspect(introspectRequest);
    }
    @GetMapping("/me")
    ResponseEntity<ResponseObject> getMe() throws APIException {
        return authencationService.getProfile();
    }
}
