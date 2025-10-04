package com.api_coffee_store.api_coffee_store.dtos.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record CreateAddressReq(
        @NotNull String userId,
        String receiverName,
        @Column(length = 10) String phone,
        String line1,
        String line2,
        String district,
        String city,
        Double latitude,
        Double longitude,
        boolean isDefault
) {
}
