package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.response.AuthenticationGoogleResponse;
import com.api_coffee_store.api_coffee_store.dtos.response.AuthenticationResponse;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.User;
import com.api_coffee_store.api_coffee_store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {
    private final UserRepository userRepo;
    private final AuthencationService authencationService;

    @Value("${GOOGLE_CLIENT_ID}") private String googleClientId;

    public ResponseEntity<ResponseObject> loginWithGoogleIdToken(String idToken) {
        var verifier = new com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance())
                .setAudience(java.util.Collections.singletonList(googleClientId))
                .build();

        com.google.api.client.googleapis.auth.oauth2.GoogleIdToken token;
        try {
            token = verifier.verify(idToken);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Google ID token", e);
        }
        if (token == null) throw new RuntimeException("Invalid Google ID token");

        var payload = token.getPayload();
        String sub     = payload.getSubject(); // unique id
        String email   = (String) payload.getEmail();
        String name    = (String) payload.get("name");
        String picture = (String) payload.get("picture");

        AtomicBoolean isNew = new AtomicBoolean(false);
        User user = userRepo.findByGoogleSub(sub).orElseGet(() -> {
            // nếu chưa có theo sub, thử theo email để link tài khoản cũ
            User u = userRepo.findByEmail(email).orElseGet(User::new);
            u.setEmail(email);
            u.setFullname(name);
            u.setGoogleSub(sub);
            isNew.set(true); // trick nhỏ, nhưng ta làm gọn hơn:
            return userRepo.save(u);
        });
        user.setFullname(name);
        userRepo.save(user);

        String jwt = authencationService.generateToken(user);
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                new ResponseObject(SuccessCode.REQUEST.getStatus(), "Login Successfully",AuthenticationGoogleResponse.builder()
                        .id(user.getId())
                        .token(jwt)
                        .fullname(user.getFullname())
                        .build())
        );
    }
}
