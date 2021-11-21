package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class BuyerRepository {
    private final EntityManager entityManager;

    public BuyerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Buyer buyer) {
        entityManager.getTransaction().begin();
        entityManager.persist(buyer);
        entityManager.getTransaction().commit();
        return buyer.getId();
    }

    public List<Buyer> findAll() {
        return entityManager.createQuery("SELECT i FROM Buyer i").getResultList();
    }

    public void removeById(UUID id) {
        Buyer found = entityManager.find(Buyer.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(found);
        entityManager.getTransaction().commit();
    }
}
