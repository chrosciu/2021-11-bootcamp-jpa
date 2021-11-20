package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class ProductRepository {
    private final EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }

    public Product findById(UUID id) {
        return entityManager.find(Product.class, id);
    }

    public void removeById(UUID id) {
        Product found = findById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(found);
        entityManager.getTransaction().commit();
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p").getResultList();
    }
}
