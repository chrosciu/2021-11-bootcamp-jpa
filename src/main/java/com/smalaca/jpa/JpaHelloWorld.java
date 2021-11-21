package com.smalaca.jpa;

import com.smalaca.jpa.domain.Comment;
import com.smalaca.jpa.domain.Description;
import com.smalaca.jpa.domain.Item;
import com.smalaca.jpa.domain.ItemRepository;
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

        ToDo withComments = new ToDo("write some comment");
        withComments.addComment(new Comment("smalaca", "comment one"));
        withComments.addComment(new Comment("smalaca", "comment two"));
        withComments.addComment(new Comment("peter parker", "comment three"));
        toDoRepository.save(withComments);

        ToDo todoItemWithTags = new ToDo("todo item with tags");
        todoItemWithTags.addTag("important", "very important thing");
        todoItemWithTags.addTag("must have", "something that must be done");
        todoItemWithTags.addTag("nice", "something I really like");
        toDoRepository.save(todoItemWithTags);

        ItemRepository itemRepository = new ItemRepository(context1);
        itemRepository.save(new Item(new Description("something good", "recipe for a diner")));
        itemRepository.save(new Item(new Description("something not so good", "I don't know can be not so good")));
        itemRepository.save(new Item(new Description("too short")));

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
        displayAll(entityManagerFactory);
    }

    private static void displayAll(EntityManagerFactory entityManagerFactory) {
        EntityManager lastContext = entityManagerFactory.createEntityManager();

        new ToDoRepository(lastContext).findAll().forEach(System.out::println);
        new ItemRepository(lastContext).findAll().forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("------------------------");
        System.out.println("----- NEXT CONTEXT -----");
        System.out.println("------------------------");
    }
}
