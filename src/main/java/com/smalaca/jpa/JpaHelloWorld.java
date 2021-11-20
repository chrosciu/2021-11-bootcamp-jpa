package com.smalaca.jpa;

import com.smalaca.jpa.domain.ToDo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        entityManager1.getTransaction().begin();
        UUID id = UUID.randomUUID();
        entityManager1.persist(new ToDo(id, "conduct a training"));

        ToDo toDo = entityManager1.find(ToDo.class, id);

        System.out.println(toDo);
        entityManager1.getTransaction().commit();
        entityManager1.close();

        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("------------------------");

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        ToDo found = entityManager2.find(ToDo.class, id);

        System.out.println(found);
        entityManager2.close();
    }
}
