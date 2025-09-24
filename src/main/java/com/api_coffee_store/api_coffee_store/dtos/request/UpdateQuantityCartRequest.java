package com.api_coffee_store.api_coffee_store.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateQuantityCartRequest {
    private String productVariantId;
    private Integer quantity;
}
