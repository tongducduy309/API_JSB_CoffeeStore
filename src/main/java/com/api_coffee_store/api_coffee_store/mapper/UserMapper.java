package com.api_coffee_store.api_coffee_store.mapper;


import com.api_coffee_store.api_coffee_store.dtos.response.UserResponse;
import com.api_coffee_store.api_coffee_store.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id",target = "id")
    UserResponse toUserResponse (User user);
    List<UserResponse> toResponseList(List<User> users);
}
