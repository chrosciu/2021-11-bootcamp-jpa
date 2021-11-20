package com.smalaca.jpa;

import com.smalaca.jpa.domain.ToDo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        entityManager.persist(new ToDo(UUID.randomUUID(), "conduct a training"));

        entityManager.close();
    }
}
