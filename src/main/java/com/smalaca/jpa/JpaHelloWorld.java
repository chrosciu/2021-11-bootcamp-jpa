package com.smalaca.jpa;

import com.smalaca.jpa.domain.Buyer;
import com.smalaca.jpa.domain.BuyerRepository;
import com.smalaca.jpa.domain.ContactDetails;
import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceRepository;
import com.smalaca.jpa.domain.Product;
import com.smalaca.jpa.domain.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager context1 = entityManagerFactory.createEntityManager();

        ProductRepository productRepository = new ProductRepository(context1);

        productRepository.save(new Product("Water", "The best thing to drink. On the morning, during the day and for a sleep."));
        UUID toRemoveId = productRepository.save(new Product("Bread", "Something to start with"));
        UUID toModifyId = productRepository.save(new Product("Carrot", "Because they said you will see better"));

        InvoiceRepository invoiceRepository = new InvoiceRepository(context1);
        invoiceRepository.save(Invoice.created());
        invoiceRepository.save(Invoice.created());
        invoiceRepository.save(Invoice.created());

        BuyerRepository buyerRepository = new BuyerRepository(context1);
        buyerRepository.save(new Buyer(new ContactDetails("pparker", "111111111", "peter.parker@marvel.com")));
        buyerRepository.save(new Buyer(new ContactDetails("srogers", "123456789", "captain.america@marvel.com")));
        buyerRepository.save(new Buyer(new ContactDetails("carol.d4nv3rs", "987654321", "captain@marvel.com")));

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

        displayAll(entityManagerFactory);
    }

    private static void displayAll(EntityManagerFactory entityManagerFactory) {
        EntityManager lastContext = entityManagerFactory.createEntityManager();
        ProductRepository productRepository = new ProductRepository(lastContext);
        InvoiceRepository invoiceRepository = new InvoiceRepository(lastContext);
        BuyerRepository buyerRepository = new BuyerRepository(lastContext);

        productRepository.findAll().forEach(System.out::println);
        invoiceRepository.findAll().forEach(System.out::println);
        buyerRepository.findAll().forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");
    }
}
