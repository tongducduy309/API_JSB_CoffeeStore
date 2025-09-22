package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.CategoryRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.ProductRequest;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.mapper.CategoryMapper;
import com.api_coffee_store.api_coffee_store.models.Category;
import com.api_coffee_store.api_coffee_store.models.Product;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public ResponseEntity<ResponseObject> getAllCategories(){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Categories")
                        .data(categoryRepository.findAll())
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> createCategory(CategoryRequest newCategory) {
        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.CREATE.getStatus())
                        .message("Create Product Successfully")
                        .data(categoryRepository.save(categoryMapper.toCategory(newCategory)))
                        .build()
        );



    }
}
