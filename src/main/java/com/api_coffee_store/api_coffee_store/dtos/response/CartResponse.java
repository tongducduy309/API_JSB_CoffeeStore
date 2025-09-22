package com.api_coffee_store.api_coffee_store.dtos.response;

import com.api_coffee_store.api_coffee_store.models.Product;
import com.api_coffee_store.api_coffee_store.models.ProductVariant;
import com.api_coffee_store.api_coffee_store.models.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    private String id;
    private ProductInCartResponse product;
    private ProductVariant productVariant;
    private Integer quantity;
    private String note;
}
