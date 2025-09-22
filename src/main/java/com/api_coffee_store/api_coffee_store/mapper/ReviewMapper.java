package com.api_coffee_store.api_coffee_store.mapper;

import com.api_coffee_store.api_coffee_store.dtos.request.ReviewRequest;
import com.api_coffee_store.api_coffee_store.dtos.response.ReviewResponse;
import com.api_coffee_store.api_coffee_store.models.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toReview(ReviewRequest reviewRequest);
    ReviewResponse toResponse(Review review);
    List<ReviewResponse> toResponseList(List<Review> reviews);
}
