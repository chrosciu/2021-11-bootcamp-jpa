package com.smalaca.jpa.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringAddressRepository extends CrudRepository<Address, UUID> {
    List<Address> findAllByCity(String city);
    Optional<Address> findOneByCityAndStreet(String city, String street);
    Address findOneByStreetAndCity(String street, String city);
    List<Address> findAllByCityOrStreet(String city, String street);
}
