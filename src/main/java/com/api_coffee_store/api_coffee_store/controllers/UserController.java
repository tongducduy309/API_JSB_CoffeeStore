package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.User;
import com.api_coffee_store.api_coffee_store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

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
}
