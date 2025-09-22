package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.ProductRequest;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.Product;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.ProductService;
import com.api_coffee_store.api_coffee_store.utils.StringUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    ResponseEntity<ResponseObject> getAllProducts(@RequestParam(required = false) Integer limit) throws APIException {
        return productService.getAllProducts(limit);
    }


    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable String id) throws APIException {
        return productService.getProductById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseObject> createProduct(@Valid @ModelAttribute ProductRequest newProduct) throws APIException {
        return productService.createProduct(newProduct);
    }

    @GetMapping("/search/{keyword}")
    ResponseEntity<ResponseObject> searchNoAccent(@PathVariable String keyword) {
        return productService.searchNoAccent(keyword);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }



}
