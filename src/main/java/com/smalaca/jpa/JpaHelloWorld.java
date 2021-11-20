package com.smalaca.jpa;

import com.smalaca.jpa.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        UUID id = UUID.randomUUID();
        Product product = new Product(id, "Water", "The best thing to drink");
        entityManager1.getTransaction().begin();
        entityManager1.persist(product);
        entityManager1.getTransaction().commit();

        Product found = entityManager1.find(Product.class, id);
        System.out.println(found);
        entityManager1.close();

        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println(entityManager2.find(Product.class, id));

        entityManager2.close();
    }
}
