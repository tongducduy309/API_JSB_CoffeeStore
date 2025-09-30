package com.api_coffee_store.api_coffee_store.dtos.request;

import lombok.Data;
@Data
public class CreateOrderPaymentRequest {
    private Long orderId;
    private long amount; // VND
    private String orderInfo;
}
