package com.api_coffee_store.api_coffee_store.dtos.response;

import com.api_coffee_store.api_coffee_store.models.ProductVariant;

public record CheckoutItemResponse(
        String id,
        ProductInCartResponse product,
        ProductVariant productVariant,
        Integer quantity,
        String note
) {
}
