package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.ReviewRequest;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.mapper.ReviewMapper;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.Review;
import com.api_coffee_store.api_coffee_store.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    public ResponseEntity<ResponseObject> getAllReviews(){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Reviews")
                        .data(reviewRepository.findAll())
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> createReview(ReviewRequest reviewRequest){


        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.CREATE.getStatus())
                        .message("Create Review Successfully")
                        .data(reviewRepository.save(reviewMapper.toReview(reviewRequest)))
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> getReviewByProductId(String product_id){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Review By Id Product")
                        .data(reviewMapper.toResponseList(reviewRepository.findByProductId(product_id)))
                        .build()
        );
    }
}
