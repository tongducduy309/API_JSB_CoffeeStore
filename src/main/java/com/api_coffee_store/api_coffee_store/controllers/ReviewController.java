package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.ReviewRequest;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.Review;
import com.api_coffee_store.api_coffee_store.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    ResponseEntity<ResponseObject> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @PostMapping
    ResponseEntity<ResponseObject> createReview(@Valid @RequestBody ReviewRequest review){
        return reviewService.createReview(review);
    }

    @GetMapping("/product-id/{id}")
    ResponseEntity<ResponseObject> getReviewByProductId(@PathVariable String id){
        return reviewService.getReviewByProductId(id);
    }

}
