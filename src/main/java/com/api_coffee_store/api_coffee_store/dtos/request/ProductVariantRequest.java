package com.api_coffee_store.api_coffee_store.dtos.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {
    private String size;
    private Double price;
    private boolean status;
}
