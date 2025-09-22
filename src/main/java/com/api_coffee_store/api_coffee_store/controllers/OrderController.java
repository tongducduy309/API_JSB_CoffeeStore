package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateOrderRequest;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    ResponseEntity<ResponseObject> createOrder(
           @Valid @RequestBody CreateOrderRequest createOrderRequest) throws APIException {
        return orderService.createOrder(createOrderRequest);
    }
}
