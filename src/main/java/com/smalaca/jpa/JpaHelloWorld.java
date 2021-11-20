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
        EntityManager context1 = entityManagerFactory.createEntityManager();
        ToDoRepository toDoRepository = new ToDoRepository(context1);

        toDoRepository.save(new ToDo(UUID.randomUUID(), "eat lunch"));
        toDoRepository.save(new ToDo(UUID.randomUUID(), "conduct a training"));
        toDoRepository.save(new ToDo(UUID.randomUUID(), "go to sleep"));

        context1.close();

        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("------------------------");

        EntityManager lastContext = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(lastContext);

        toDoRepository.findAll()
                .forEach(System.out::println);

        lastContext.close();
    }
}
