package com.api_coffee_store.api_coffee_store.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String receiverName;
    @Column(length = 10)
    private String phone;
    private String line1;
    private String line2;
    private String district;
    private String city;
    private Double latitude;
    private Double longitude;
    private boolean isDefault;



}
