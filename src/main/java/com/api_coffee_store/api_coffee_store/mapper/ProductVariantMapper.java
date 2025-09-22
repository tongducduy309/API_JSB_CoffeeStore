package com.api_coffee_store.api_coffee_store.mapper;

import com.api_coffee_store.api_coffee_store.dtos.request.ProductVariantRequest;
import com.api_coffee_store.api_coffee_store.models.ProductVariant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    ProductVariant toProductVariant(ProductVariantRequest productVariantRequest);
    List<ProductVariant> toListProductVariant(List<ProductVariantRequest> listProductVariantRequest);
}
