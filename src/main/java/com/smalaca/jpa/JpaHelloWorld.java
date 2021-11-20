package com.smalaca.jpa;

import com.smalaca.jpa.domain.ToDo;
import com.smalaca.jpa.domain.ToDoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        ToDoRepository toDoRepository = new ToDoRepository(entityManager1);

        UUID id = UUID.randomUUID();
        toDoRepository.save(new ToDo(id, "conduct a training"));

        entityManager1.close();

        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("------------------------");

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(entityManager2);

        toDoRepository.findAll()
                .forEach(System.out::println);

        entityManager2.close();
    }
}
