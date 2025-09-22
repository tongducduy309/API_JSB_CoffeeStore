package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateBlogRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.CreateOrderRequest;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    @GetMapping
    ResponseEntity<ResponseObject> getAllPosts(){
        return blogService.getAllBlogs();
    }
    @PostMapping
    ResponseEntity<ResponseObject> createBlog(
            @Valid @RequestBody CreateBlogRequest createBlogRequest) throws APIException {
        return blogService.createBlog(createBlogRequest);
    }
    @GetMapping("/html/{slug}")
    ResponseEntity<ResponseObject> getBlogBySlug(@PathVariable String slug) throws APIException{
        return blogService.getBlogBySlug(slug);
    }
}
