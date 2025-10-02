package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
}
