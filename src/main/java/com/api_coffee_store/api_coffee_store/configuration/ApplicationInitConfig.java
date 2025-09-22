package com.api_coffee_store.api_coffee_store.configuration;


import com.api_coffee_store.api_coffee_store.enums.Role;
import com.api_coffee_store.api_coffee_store.models.User;
import com.api_coffee_store.api_coffee_store.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByEmail("admin@dev.com").isEmpty()){
                HashSet<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .fullname("Admin")
                        .password(passwordEncoder.encode("GENER"))
                        .roles(roles)
                        .email("admin@dev.com")
                        .build();

                User newUser = userRepository.save(user);
                log.info(newUser.getId());

                log.info("Account Admin Has Been Created");
            }
        };
    }
}
