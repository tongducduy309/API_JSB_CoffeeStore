package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateOrderRequest;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    ResponseEntity<ResponseObject> getAllOrders(){
        return orderService.getAllOrders();
    }
    @PostMapping
    ResponseEntity<?> createOrder(
           @Valid @RequestBody CreateOrderRequest createOrderRequest, HttpServletRequest http) throws APIException {
        return orderService.createOrder(createOrderRequest,http);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getOrderById(@PathVariable Long id) throws APIException {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/payment-status")
    ResponseEntity<ResponseObject> getPaymentStatus(@PathVariable Long id) throws APIException {
        return orderService.getPaymentStatus(id);
    }

    @GetMapping("/myorders")
    ResponseEntity<ResponseObject> getMyOrder() throws APIException {
        return orderService.getMyOrder();
    }


}
