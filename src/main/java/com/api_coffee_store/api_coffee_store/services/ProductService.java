package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.ProductRequest;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.mapper.ProductVariantMapper;
import com.api_coffee_store.api_coffee_store.models.Category;
import com.api_coffee_store.api_coffee_store.models.Product;
import com.api_coffee_store.api_coffee_store.models.ProductVariant;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.repositories.CategoryRepository;
import com.api_coffee_store.api_coffee_store.repositories.ProductRepository;
import com.api_coffee_store.api_coffee_store.utils.StringUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ProductVariantMapper productVariantMapper;

    public ResponseEntity<ResponseObject> getAllProducts(){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Products")
                        .data(productRepository.findAll())
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> getProductById(String id) throws APIException {
        Product product = productRepository.findById(id).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND));

        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Found Product With Id = "+id)
                        .data(product)
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> getAllProducts(Integer limit) throws APIException {

        List<Product> products = new ArrayList<>();
        if(limit==null)
            products = productRepository.findAllByStatusTrue(Sort.by(Sort.Direction.DESC, "createdAt"));
        else{
            Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
            products = productRepository.findAllByStatusTrue(pageable).getContent();
        }
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Products With Condition")
                        .data(products)
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseObject> createProduct(ProductRequest newProduct) throws APIException {
        Category category = categoryRepository.findById(newProduct.getCategoryId()).orElseThrow(()->APIException.builder()
                .status(ErrorCode.NOT_FOUND.getStatus())
                .message("Cannot Found Category With Id = "+newProduct.getCategoryId())
                .httpStatusCode(ErrorCode.NOT_FOUND.getHttpStatusCode())
                .build());
        try{

            String generatedFileName = (newProduct.getImage()==null||newProduct.getImage().isEmpty())?"":storageService.storeFile(newProduct.getImage());

            Product product = Product.builder()
                    .name(newProduct.getName())
                    .variants(new ArrayList<>())
                    .description(newProduct.getDescription())
                    .category(category)
                    .img(generatedFileName)
                    .status(newProduct.isStatus())
                    .build();

            List<ProductVariant> variants = newProduct.getVariants().stream()
                    .map(v -> ProductVariant.builder()
                            .size(v.getSize())
                            .price(v.getPrice())
                            .product(product)
                            .build())
                    .toList();
            product.setVariants(variants);
            return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                    ResponseObject.builder()
                            .status(SuccessCode.CREATE.getStatus())
                            .message("Create Product Successfully")
                            .data(productRepository.save(product))
                            .build()
            );
        }catch (Exception exception){
            return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatusCode()).body(
                    ResponseObject.builder()
                            .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                            .message(exception.getMessage())
                            .build()
            );
        }







    }



    public ResponseEntity<ResponseObject> searchNoAccent(String keyword) {
        String normalizedKeyword = StringUtil.normalize(keyword).toLowerCase();
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Search Successfully")
                        .data(productRepository.findAll().stream()
                                .filter(p -> StringUtil.normalize(p.getName()).toLowerCase().contains(normalizedKeyword))
                                .toList())
                        .build() );
    }


    public ResponseEntity<ResponseObject> deleteProduct(String id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
            imageStorageService.deleteFile(product.get().getImg());

        }
        return ResponseEntity.status(SuccessCode.NO_CONTENT.getHttpStatusCode()).body(
                new ResponseObject(SuccessCode.NO_CONTENT.getStatus(), "Delete Product Successfully","")
        );
    }



}
