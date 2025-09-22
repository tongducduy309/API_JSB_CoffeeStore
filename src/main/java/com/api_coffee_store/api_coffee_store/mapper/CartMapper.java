package com.api_coffee_store.api_coffee_store.mapper;

import com.api_coffee_store.api_coffee_store.dtos.request.CartRequest;
import com.api_coffee_store.api_coffee_store.dtos.response.CartResponse;
import com.api_coffee_store.api_coffee_store.models.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(CartRequest cartRequest);
    CartResponse toResponse(Cart cart);
    List<CartResponse> toListResponse(List<Cart> carts);
}
