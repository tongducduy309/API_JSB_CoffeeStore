package com.api_coffee_store.api_coffee_store.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.CrossOriginEmbedderPolicyHeaderWriter;
import org.springframework.security.web.header.writers.CrossOriginOpenerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String SIGNER_KEY;


    private final String[] PUBLIC_ENDPOINT_POST = {"/api/v1/auth/authenticate","/api/v1/auth/introspect","/api/v1/users",
    "/api/v1/reviews","/api/v1/auth/google", "/api/v1/payments/**"};
    private final String[] PUBLIC_ENDPOINT_GET = {"/api/v1/reviews","/api/v1/blogs/html/{slug}","/api/v1/products","/api/v1/blogs",
            "/api/v1/files", "/api/v1/auth/me", "/api/v1/products/{id}", "/api/v1/reviews/product-id/{id}", "/api/v1/payments/**"};
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(h -> h
                        .crossOriginOpenerPolicy(coop -> coop.policy(CrossOriginOpenerPolicyHeaderWriter.CrossOriginOpenerPolicy.SAME_ORIGIN_ALLOW_POPUPS))
                        .crossOriginEmbedderPolicy(coep -> coep.policy(CrossOriginEmbedderPolicyHeaderWriter.CrossOriginEmbedderPolicy.UNSAFE_NONE))
                )
                .authorizeHttpRequests(request->
                request.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINT_POST).permitAll()
                        .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINT_GET).permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/v1/users").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                        ).authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                        .accessDeniedHandler(new JsonAccessDeniedHandler())

                );

        httpSecurity.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);




        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}