package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.AddressUpdateReq;
import com.api_coffee_store.api_coffee_store.dtos.request.CreateAddressReq;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.User;
import com.api_coffee_store.api_coffee_store.services.AddressService;
import com.api_coffee_store.api_coffee_store.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final AddressService addressService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> createUser(@RequestBody User user) throws APIException {
        return userService.createUser(user);
    }

    @GetMapping("/profile")
    ResponseEntity<ResponseObject> getProfile() throws APIException {
        return userService.getProfileByToken();
    }

    @PostMapping("/addresses")
    ResponseEntity<ResponseObject> createAddress(@RequestBody CreateAddressReq req) throws APIException {
        return addressService.createAddress(req);
    }

    @PutMapping("/addresses/{id}")
    ResponseEntity<ResponseObject> updateAddress(@PathVariable Long id,@RequestBody AddressUpdateReq req) throws APIException {
        return addressService.updateAddress(id,req);
    }

    @DeleteMapping("/addresses/{id}")
    ResponseEntity<ResponseObject> deleteAddress(@PathVariable Long id) throws APIException {
        return addressService.deleteById(id);
    }

    @PutMapping("/addresses/{id}/default")
    ResponseEntity<ResponseObject> setDefaultAddress(@PathVariable Long id) throws APIException {
        return addressService.setDefault(id);
    }

    @GetMapping("/addresses")
    ResponseEntity<ResponseObject> getMyAddress() throws APIException {
        return addressService.getMyAddress();
    }
}
