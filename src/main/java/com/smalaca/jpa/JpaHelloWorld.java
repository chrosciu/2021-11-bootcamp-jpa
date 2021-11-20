package com.smalaca.jpa;

import com.smalaca.jpa.domain.Product;
import com.smalaca.jpa.domain.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        UUID id = UUID.randomUUID();
        ProductRepository productRepository = new ProductRepository(entityManager1);

        productRepository.save(new Product(id, "Water", "The best thing to drink"));

        Product found = productRepository.findById(id);
        System.out.println(found);

        entityManager1.close();

        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        productRepository = new ProductRepository(entityManager2);

        System.out.println(productRepository.findById(id));

        entityManager2.close();
    }
}
