package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class AuthorRepository {
    private final EntityManager entityManager;

    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Author author) {
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();

        return author.getId();
    }

    public List<Author> findAll() {
        return entityManager.createQuery("SELECT a FROM Author a").getResultList();
    }

    public void removeById(UUID id) {
        Author toRemove = entityManager.find(Author.class, id);

        entityManager.getTransaction().begin();
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();

    }
}
