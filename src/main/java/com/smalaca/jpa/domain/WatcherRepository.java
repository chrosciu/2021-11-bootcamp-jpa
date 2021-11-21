package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class WatcherRepository {
    private final EntityManager entityManager;

    public WatcherRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Watcher watcher) {
        entityManager.getTransaction().begin();
        entityManager.persist(watcher);
        entityManager.getTransaction().commit();

        return watcher.getId();
    }

    public List<Watcher> findAll() {
        return entityManager.createQuery(
                "SELECT watcher FROM Watcher watcher").getResultList();
    }

    public void removeById(UUID id) {
        Watcher toRemove = entityManager.find(Watcher.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
    }
}
