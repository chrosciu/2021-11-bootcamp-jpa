package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class SellerRepository {
    private final EntityManager entityManager;

    public SellerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Seller seller) {
        entityManager.getTransaction().begin();
        entityManager.persist(seller);
        entityManager.getTransaction().commit();
        return seller.getId();
    }

    public List<Seller> findAll() {
        return entityManager.createQuery("SELECT i FROM Seller i").getResultList();
    }
}
