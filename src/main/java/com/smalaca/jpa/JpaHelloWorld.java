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

        UUID toRemoveId = UUID.randomUUID();
        productRepository.save(new Product(UUID.randomUUID(), "Water", "The best thing to drink"));
        productRepository.save(new Product(toRemoveId, "Bread", "Something to start with"));
        productRepository.save(new Product(UUID.randomUUID(), "Carrot", "Because they said you will see better"));

        context1.close();

        nextContext();
        EntityManager context2 = entityManagerFactory.createEntityManager();
        productRepository = new ProductRepository(context2);

        Product found = productRepository.findById(toRemoveId);
        context2.getTransaction().begin();
        context2.remove(found);
        context2.getTransaction().commit();

        nextContext();

        EntityManager lastContext = entityManagerFactory.createEntityManager();
        productRepository = new ProductRepository(lastContext);

        List<Product> products = lastContext.createQuery("SELECT p FROM Product p").getResultList();
        products.forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");
    }
}
