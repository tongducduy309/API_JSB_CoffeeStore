package com.api_coffee_store.api_coffee_store.mapper;

import com.api_coffee_store.api_coffee_store.dtos.response.ProductInCartResponse;
import com.api_coffee_store.api_coffee_store.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductInCartResponse toProductInCartResponse(Product product);
}
