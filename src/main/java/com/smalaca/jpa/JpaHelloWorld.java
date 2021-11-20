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

        UUID toRemoveId = UUID.randomUUID();
        toDoRepository.save(new ToDo(toRemoveId, "eat lunch"));
        toDoRepository.save(new ToDo(UUID.randomUUID(), "conduct a training"));
        toDoRepository.save(new ToDo(UUID.randomUUID(), "go to sleep"));

        context1.close();

        nextContext();
        EntityManager context2 = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(context2);

        toDoRepository.removeById(toRemoveId);

        context2.close();
        nextContext();

        EntityManager lastContext = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(lastContext);

        toDoRepository.findAll()
                .forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("------------------------");
        System.out.println("----- NEXT CONTEXT -----");
        System.out.println("------------------------");
    }
}
