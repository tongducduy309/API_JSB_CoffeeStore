package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.response.UserResponse;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.Role;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.mapper.UserMapper;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.User;
import com.api_coffee_store.api_coffee_store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseObject> createUser(@RequestBody User user) throws APIException {
        if (userRepository.existsByEmail(user.getEmail())) throw new APIException(ErrorCode.EMAIL_EXISTS);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(200,"Create User Successfully",userRepository.save(user))
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"All Users",userMapper.toResponseList(userRepository.findAll()))
        );
    }

    public ResponseEntity<ResponseObject> getProfileByToken() throws APIException {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Profile",userMapper.toUserResponse(getProfile()))
        );
    }

    public User getProfile() throws APIException {
        var context = SecurityContextHolder.getContext();
        String name =  context.getAuthentication().getName();
        return userRepository.findByEmail(name).orElseThrow(()->
                new APIException(ErrorCode.USER_NOT_EXISTS));
    }
}
