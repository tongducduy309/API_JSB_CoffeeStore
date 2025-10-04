package com.api_coffee_store.api_coffee_store.dtos.request;


public record AddressUpdateReq(
        String receiverName,
        String phone,
        String line1,
        String line2,
        String district,
        String city,
        Double latitude,
        Double longitude,
        Boolean isDefault
) {

}
