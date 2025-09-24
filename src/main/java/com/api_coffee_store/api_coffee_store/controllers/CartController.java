package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.UpdateQuantityCartRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.CartRequest;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping
    ResponseEntity<ResponseObject> getAllCarts(){
        return cartService.getAllCarts();
    }
    @PostMapping
    ResponseEntity<ResponseObject> insertCart(@RequestBody CartRequest cartRequest) throws APIException {
        return cartService.insertCart(cartRequest);
    }

    @GetMapping("/mycart")
    ResponseEntity<ResponseObject> getMyCart() throws APIException {

        return cartService.getMyCart();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteCart(@PathVariable String id){
        return cartService.removeCart(id);
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> updateCart(@PathVariable String id,@RequestBody UpdateQuantityCartRequest cartPatchRequest) throws APIException {
        return cartService.updateCart(id,cartPatchRequest);
    }

}
