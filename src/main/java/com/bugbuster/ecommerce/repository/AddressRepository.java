package com.bugbuster.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugbuster.ecommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
