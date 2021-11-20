package com.smalaca.jpa;

import com.smalaca.jpa.domain.Description;
import com.smalaca.jpa.domain.ToDo;
import com.smalaca.jpa.domain.ToDoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");

        // CREATE
        EntityManager context1 = entityManagerFactory.createEntityManager();
        ToDoRepository toDoRepository = new ToDoRepository(context1);

        UUID toRemoveId = toDoRepository.save(new ToDo("eat lunch"));
        toDoRepository.save(new ToDo("conduct a training"));
        UUID toModifyId = toDoRepository.save(new ToDo("go to sleep"));

        ToDo withDescription = new ToDo("eat dinner");
        withDescription.add(new Description("something good", "recipe for a diner"));
        toDoRepository.save(withDescription);

        context1.close();

        nextContext();

        // REMOVE
        EntityManager context2 = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(context2);

        toDoRepository.removeById(toRemoveId);

        context2.close();
        nextContext();

        // UPDATE
        EntityManager context3 = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(context3);

        ToDo toModify = toDoRepository.findById(toModifyId);
        toModify.changeSubjectTo("go to sleep but not too early");

        toDoRepository.update(toModify);

        context3.close();
        nextContext();

        // SHOW ALL
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
