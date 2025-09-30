package com.api_coffee_store.api_coffee_store.dtos.request;

import com.api_coffee_store.api_coffee_store.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePaymentStatusReq(
        @NotNull Long orderId,
        @NotNull PaymentStatus status
        ) {
}
