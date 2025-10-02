package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,String> {
}
