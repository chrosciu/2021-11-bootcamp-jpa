package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;

public class AuthorRepository {
    private final EntityManager entityManager;

    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Author author) {
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();
    }

    public List<Author> findAll() {
        return entityManager.createQuery("SELECT a FROM Author a").getResultList();
    }
}
