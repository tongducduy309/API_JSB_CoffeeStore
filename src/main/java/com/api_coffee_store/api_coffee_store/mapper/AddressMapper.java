package com.api_coffee_store.api_coffee_store.mapper;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateAddressReq;
import com.api_coffee_store.api_coffee_store.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(CreateAddressReq req);

}
