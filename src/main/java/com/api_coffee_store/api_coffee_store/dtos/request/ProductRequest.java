package com.api_coffee_store.api_coffee_store.dtos.request;

import com.api_coffee_store.api_coffee_store.models.ProductVariant;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Product Name Is Required")
    @Size(max = 100, message = "Product Name Must Not Exceed 100 Characters")
    @Column(unique = true)
    private String name;
    @NotEmpty
    private List<ProductVariantRequest> variants;
    private String description;
    private MultipartFile image;
    @NotEmpty(message = "Category Id Is Required")
    private String categoryId;
    private boolean status;



}
