package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressRepository {
    private final EntityManager entityManager;

    public AddressRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Address> findAll() {
        return entityManager.createQuery(
                "SELECT address FROM Address address").getResultList();
    }
}
