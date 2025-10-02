package com.api_coffee_store.api_coffee_store.dtos.response;

import com.api_coffee_store.api_coffee_store.models.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @Column(name = "id")
    private String id;
    private String fullname;
    private String email;
    private int point;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String googleSub;
    private List<Address> addresses;
}
