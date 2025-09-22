package com.api_coffee_store.api_coffee_store.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {
    private String productId;
    private String productVariantId;
    private Integer quantity;
    private String note;
}
