package com.smalaca.jpa;

import com.smalaca.jpa.domain.Address;
import com.smalaca.jpa.domain.AddressRepository;
import com.smalaca.jpa.domain.Author;
import com.smalaca.jpa.domain.AuthorRepository;
import com.smalaca.jpa.domain.Comment;
import com.smalaca.jpa.domain.Description;
import com.smalaca.jpa.domain.Item;
import com.smalaca.jpa.domain.ItemRepository;
import com.smalaca.jpa.domain.ToDo;
import com.smalaca.jpa.domain.ToDoCategory;
import com.smalaca.jpa.domain.ToDoRepository;
import com.smalaca.jpa.domain.Watcher;

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

        ToDo todoWithTodoCategory = new ToDo("Todo with todo category");
        todoWithTodoCategory.setCategory(new ToDoCategory("WORK", "item that needs to be done at work"));
        toDoRepository.save(todoWithTodoCategory);

        AuthorRepository authorRepository = new AuthorRepository(context1);
        Author tonyStark = new Author("Tony", "Stark");
        tonyStark.add(new Address("street", "city", "postal code", "country"));
        tonyStark.add(new Address("Floriańska", "Kraków", "12-345", "Polska"));
        tonyStark.add(new Address("Aleja Jana Pawła II", "Kraków", "98-765", "Polska"));
        authorRepository.save(tonyStark);

        Author steveRogers = new Author("Steve", "Rogers");
        steveRogers.add(new Address("Grodzka", "Kraków", "34-453", "Polska"));
        Address addressToRemove = new Address("Sienna", "Kraków", "00-999", "Polska");
        steveRogers.add(addressToRemove);
        UUID toRemoveSteveRogersId = authorRepository.save(steveRogers);

        Author sebastianMalaca = new Author("Sebastian", "Malaca");
        authorRepository.save(sebastianMalaca);
        Author peterParker = new Author("Peter", "Parker");
        authorRepository.save(peterParker);

        ToDo withAnAuthor = new ToDo("with an author");
        withAnAuthor.set(sebastianMalaca);
        toDoRepository.save(withAnAuthor);

        ToDo withAnAnotherAuthor = new ToDo("with an another author");
        withAnAnotherAuthor.set(peterParker);
        UUID todoWithAuthorId = toDoRepository.save(withAnAnotherAuthor);

        ToDo withWatchers = new ToDo("with Watchers");
        withWatchers.add(new Watcher("wolverine"));
        withWatchers.add(new Watcher("cyclop"));
        withWatchers.add(new Watcher("profesorX"));

        ToDo watchedBySomeone = new ToDo("item that is watched by someone");
        watchedBySomeone.add(new Watcher("jeanGrey"));
        watchedBySomeone.add(new Watcher("jubilee"));
        watchedBySomeone.add(new Watcher("shadow cat"));

        toDoRepository.save(withWatchers);
        toDoRepository.save(watchedBySomeone);

        ItemRepository itemRepository = new ItemRepository(context1);
        itemRepository.save(new Item(new Description("something good", "recipe for a diner")));
        itemRepository.save(new Item(new Description("something not so good", "I don't know can be not so good")));
        itemRepository.save(new Item(new Description("too short")));

        context1.close();

        nextContext();

        // REMOVE
        EntityManager context2 = entityManagerFactory.createEntityManager();
        toDoRepository = new ToDoRepository(context2);
        AddressRepository addressRepository = new AddressRepository(context2);
        authorRepository = new AuthorRepository(context2);

        toDoRepository.removeById(toRemoveId);
        authorRepository.removeById(toRemoveSteveRogersId);
//        addressRepository.removeById(addressToRemove.getId());
        toDoRepository.removeById(todoWithAuthorId);

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
        new AuthorRepository(lastContext).findAll().forEach(System.out::println);
        new AddressRepository(lastContext).findAll().forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("------------------------");
        System.out.println("----- NEXT CONTEXT -----");
        System.out.println("------------------------");
    }
}
