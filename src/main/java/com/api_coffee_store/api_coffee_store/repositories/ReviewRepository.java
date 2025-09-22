package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.dtos.response.ReviewResponse;
import com.api_coffee_store.api_coffee_store.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,String> {
    List<Review> findByProductId(String id);

}
