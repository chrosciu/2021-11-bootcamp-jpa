package com.smalaca.jpa.domain;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class ToDoRepository {
    private final EntityManager entityManager;

    public ToDoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(ToDo toDo) {
        entityManager.getTransaction().begin();
        entityManager.persist(toDo);
        entityManager.getTransaction().commit();
    }

    public ToDo findById(UUID id) {
        return entityManager.find(ToDo.class, id);
    }

    public List<ToDo> findAll() {
        return entityManager.createQuery(
                "SELECT todo FROM ToDo todo ORDER BY subject ASC").getResultList();
    }

    public void removeById(UUID id) {
        ToDo toRemove = findById(id);

        entityManager.getTransaction().begin();
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
    }

    public void update(ToDo toDo) {
        entityManager.getTransaction().begin();
        entityManager.merge(toDo);
        entityManager.getTransaction().commit();
    }
}
