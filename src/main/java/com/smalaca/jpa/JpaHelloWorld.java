package com.smalaca.jpa;

import com.smalaca.jpa.domain.Product;
import com.smalaca.jpa.domain.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager context1 = entityManagerFactory.createEntityManager();

        ProductRepository productRepository = new ProductRepository(context1);

        productRepository.save(new Product("Water", "The best thing to drink. On the morning, during the day and for a sleep."));
        UUID toRemoveId = productRepository.save(new Product("Bread", "Something to start with"));
        UUID toModifyId = productRepository.save(new Product("Carrot", "Because they said you will see better"));

        context1.close();

        nextContext();
        EntityManager context2 = entityManagerFactory.createEntityManager();
        productRepository = new ProductRepository(context2);

        productRepository.removeById(toRemoveId);

        nextContext();
        EntityManager context3 = entityManagerFactory.createEntityManager();
        productRepository = new ProductRepository(context3);

        Product toModify = productRepository.findById(toModifyId);
        toModify.changeDescriptionTo("Just carrot");
        productRepository.update(toModify);

        nextContext();

        EntityManager lastContext = entityManagerFactory.createEntityManager();
        productRepository = new ProductRepository(lastContext);

        List<Product> products = productRepository.findAll();
        products.forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");
    }
}
