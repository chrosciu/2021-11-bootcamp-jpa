package com.smalaca.jpa;

import com.smalaca.jpa.domain.Address;
import com.smalaca.jpa.domain.Author;
import com.smalaca.jpa.domain.SpringAddressRepository;
import com.smalaca.jpa.domain.ToDo;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class JpaIsFun {
    private final SpringAddressRepository springAddressRepository;
    private final SpringAuthorRepository springAuthorRepository;
    private final SpringToDoRepository springToDoRepository;
    private final SpringItemRepository springItemRepository;
    private final SpringWatcherRepository springWatcherRepository;

    public JpaIsFun(
            SpringAddressRepository springAddressRepository, SpringAuthorRepository springAuthorRepository,
            SpringToDoRepository springToDoRepository, SpringItemRepository springItemRepository,
            SpringWatcherRepository springWatcherRepository) {
        this.springAddressRepository = springAddressRepository;
        this.springAuthorRepository = springAuthorRepository;
        this.springToDoRepository = springToDoRepository;
        this.springItemRepository = springItemRepository;
        this.springWatcherRepository = springWatcherRepository;
    }

    @EventListener
    @Transactional
    public void test(ContextRefreshedEvent event) {
        springToDoRepository.save(new ToDo("eat lunch"));
        springToDoRepository.save(new ToDo("conduct a training"));
        springToDoRepository.save(new ToDo("go to sleep"));

        Author tonyStark = new Author("Tony", "Stark");
        tonyStark.add(new Address("street", "city", "postal code", "country"));
        tonyStark.add(new Address("Floriańska", "Kraków", "12-345", "Polska"));
        tonyStark.add(new Address("Aleja Jana Pawła II", "Kraków", "98-765", "Polska"));
        springAuthorRepository.save(tonyStark);

        Author steveRogers = new Author("Steve", "Rogers");
        steveRogers.add(new Address("Grodzka", "Kraków", "34-453", "Polska"));
        Address addressToRemove = new Address("Sienna", "Kraków", "00-999", "Polska");
        steveRogers.add(addressToRemove);
        springAuthorRepository.save(steveRogers);

        displayAll();
    }

    private void displayAll() {
        springToDoRepository.findAll().forEach(System.out::println);
        springItemRepository.findAll().forEach(System.out::println);
        springAuthorRepository.findAll().forEach(System.out::println);
        springAddressRepository.findAll().forEach(System.out::println);
        springWatcherRepository.findAll().forEach(System.out::println);
    }

    private void nextContext() {
        System.out.println("------------------------");
        System.out.println("----- NEXT CONTEXT -----");
        System.out.println("------------------------");
    }
}
