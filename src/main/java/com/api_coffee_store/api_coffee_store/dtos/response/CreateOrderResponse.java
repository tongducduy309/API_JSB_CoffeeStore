package com.api_coffee_store.api_coffee_store.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderResponse(
        String orderCode,
        Long orderId,
        long totalAmount,
        LocalDateTime createdAt,
        List<Line> lines,
        String note
) {
    public record Line(String productId,
            String productVariantId,
            long price,
            Integer quantity,
            String note) {}
}
