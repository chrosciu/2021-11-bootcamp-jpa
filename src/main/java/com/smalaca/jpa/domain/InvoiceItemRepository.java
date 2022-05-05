package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class InvoiceItemRepository {
    private final EntityManager entityManager;

    public InvoiceItemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(InvoiceItem invoiceItem) {
        entityManager.getTransaction().begin();
        entityManager.persist(invoiceItem);
        entityManager.getTransaction().commit();
        return invoiceItem.getId();
    }

    public List<InvoiceItem> findAll() {
        return entityManager.createQuery("SELECT i FROM InvoiceItem i", InvoiceItem.class).getResultList();
    }
}
