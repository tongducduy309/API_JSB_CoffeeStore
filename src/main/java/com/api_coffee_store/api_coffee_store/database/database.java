package com.api_coffee_store.api_coffee_store.database;

import com.api_coffee_store.api_coffee_store.models.*;
import com.api_coffee_store.api_coffee_store.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
@Slf4j
public class database {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, ProductRepository productRepository,
                                   ReviewRepository reviewRepository, CategoryRepository categoryRepository,
                                   CartRepository cartRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Category categoryA =  Category.builder()
//                        .name("Trà sữa")
//
//                        .build();
//                log.info("Insert Category",categoryRepository.save(categoryA));
//                User userA = User.builder()
//                        .email("user@test.com")
//                        .password(passwordEncoder.encode("12345678"))
//                        .fullname("User")
//                        .build();
//                userRepository.save(userA);
////                Product productA = Product.builder()
////                        .name("Trà Sữa Phúc Long")
////                        .variants(new ArrayList<>())
////                        .description("Béo Ngọt")
////                        .category(categoryA)
////                        .build();
////                log.info("Insert Product",productRepository.save(productA));
//                Product productB = Product.builder()
//                        .name("Trà Sữa Phúc Long B")
//                        .variants(new ArrayList<>())
//                        .description("Béo Ngọt")
//                        .category(categoryA)
//                        .build();
//                log.info("Insert Product",productRepository.save(productB));
//                Product productC = Product.builder()
//                        .name("Trà Sữa Phúc Long C")
//                        .variants(new ArrayList<>())
//                        .description("Béo Ngọt")
//                        .category(categoryA)
//                        .status(false)
//                        .build();
//                log.info("Insert Product",productRepository.save(productC));

//                Cart cart = Cart.builder()
//                        .product(productB)
//                        .user(userA)
//                        .quantity(2)
//                        .note("123")
//                        .build();
//                cartRepository.save(cart);
//                Review review = Review.builder()
//                        .point(4)
//                        .name("13")
//                        .productNameId("tra-sua")
//                        .email("123")
//                        .comment("123")
//                        .build();
//                reviewRepository.save(review);

//                Product productA = new Product("Iphone",2025,2400.0,"");
//                Product productB = new Product("Samsung",2025,2200.0,"");
//                logger.info("insert data"+productRepository.save(productA));
//                logger.info("insert data"+productRepository.save(productB));
            }
        };
    }
}
