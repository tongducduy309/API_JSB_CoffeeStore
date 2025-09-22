package com.api_coffee_store.api_coffee_store.dtos.request;

import com.api_coffee_store.api_coffee_store.enums.OrderType;
import com.api_coffee_store.api_coffee_store.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import java.util.List;

public record CreateOrderRequest(
        String userId,
        @NotEmpty List<Item> items,
        @NotNull (message = "Order Type Is Required") OrderType orderType,
        @NotNull (message = "Payment Method Is Required") PaymentMethod paymentMethod,
        String note
) {
    public record Item(
            @NotBlank String productVariantId,
            @NotNull @Positive Integer quantity,
            String note
    ) {}
}
