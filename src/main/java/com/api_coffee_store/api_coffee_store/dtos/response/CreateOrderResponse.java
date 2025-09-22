package com.api_coffee_store.api_coffee_store.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderResponse(
        String orderCode,
        String orderId,
        Double totalAmount,
        LocalDateTime createdAt,
        List<Line> lines,
        String note
) {
    public record Line(String productId,
            String productVariantId,
            Double price,
            Integer quantity,
            String note) {}
}
