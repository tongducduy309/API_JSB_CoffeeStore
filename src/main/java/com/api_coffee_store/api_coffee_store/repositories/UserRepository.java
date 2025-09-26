package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleSub(String sub);
}
