package com.smalaca.jpa;

import com.smalaca.jpa.domain.Basket;
import com.smalaca.jpa.domain.BasketIdentifier;
import com.smalaca.jpa.domain.BasketRepository;
import com.smalaca.jpa.domain.Buyer;
import com.smalaca.jpa.domain.BuyerRepository;
import com.smalaca.jpa.domain.ContactDetails;
import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceItemRepository;
import com.smalaca.jpa.domain.InvoiceRepository;
import com.smalaca.jpa.domain.Offer;
import com.smalaca.jpa.domain.OfferItem;
import com.smalaca.jpa.domain.OfferRepository;
import com.smalaca.jpa.domain.Product;
import com.smalaca.jpa.domain.ProductRepository;
import com.smalaca.jpa.domain.Rating;
import com.smalaca.jpa.domain.Seller;
import com.smalaca.jpa.domain.SellerRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager context1 = entityManagerFactory.createEntityManager();

        ProductRepository productRepository = new ProductRepository(context1);

        productRepository.save(new Product("Water", "The best thing to drink. On the morning, during the day and for a sleep."));
        UUID toRemoveId = productRepository.save(new Product("Bread", "Something to start with"));
        UUID toModifyId = productRepository.save(new Product("Carrot", "Because they said you will see better"));

        Product productWithCategories = new Product("Box", "Bigger than you expect");
        productWithCategories.addCategory("boxes");
        productWithCategories.addCategory("stuff");
        productRepository.save(productWithCategories);

        Product productWithRatings = new Product("Paper", "You can write on it");
        productWithRatings.add(new Rating("pparker", 5));
        productWithRatings.add(new Rating("wanda maximoff", 3));
        productWithRatings.add(new Rating("tony-stark", 10, "because of origami"));
        productRepository.save(productWithRatings);

        InvoiceItemRepository invoiceItemRepository = new InvoiceItemRepository(context1);
        invoiceItemRepository.save(new InvoiceItem(productWithRatings, 13));
        invoiceItemRepository.save(new InvoiceItem(productWithCategories, 7));

        InvoiceRepository invoiceRepository = new InvoiceRepository(context1);
        invoiceRepository.save(Invoice.created());
        invoiceRepository.save(Invoice.created());
        invoiceRepository.save(Invoice.created());

        OfferRepository offerRepository = new OfferRepository(context1);
        Offer offer1 = new Offer("QWERTY");
        offerRepository.save(offer1);

        Offer offerWithItems = new Offer("ABCDEF");
        offerWithItems.add(new OfferItem(UUID.randomUUID(), 13));
        offerWithItems.add(new OfferItem(UUID.randomUUID(), 42));
        offerWithItems.add(new OfferItem(UUID.randomUUID(), 7));
        offerWithItems.add(new OfferItem(UUID.randomUUID(), 100));
        offerRepository.save(offerWithItems);

        Invoice invoice = Invoice.created();
        invoice.add(offer1);
        invoiceRepository.save(invoice);

        BuyerRepository buyerRepository = new BuyerRepository(context1);
        buyerRepository.save(new Buyer(new ContactDetails("pparker", "111111111", "peter.parker@marvel.com")));
        buyerRepository.save(new Buyer(new ContactDetails("srogers", "123456789", "captain.america@marvel.com")));
        buyerRepository.save(new Buyer(new ContactDetails("carol.d4nv3rs", "987654321", "captain@marvel.com")));

        SellerRepository sellerRepository = new SellerRepository(context1);
        sellerRepository.save(new Seller(new ContactDetails("natasha", "000111222", "romanoff@marvel.com")));
        sellerRepository.save(new Seller(new ContactDetails("hawk", "123123123", "eye@marvel.com")));

        BasketRepository basketRepository = new BasketRepository(context1);
        Basket basket1 = new Basket(new BasketIdentifier("smalaca", 13, LocalDate.of(2021, 1, 20)));
        basket1.addProducts(UUID.randomUUID(), 13);
        basket1.addProducts(UUID.randomUUID(), 42);
        basket1.addProducts(UUID.randomUUID(), 1);
        basketRepository.save(basket1);
        basketRepository.save(new Basket(new BasketIdentifier("hawkeye", 42, LocalDate.of(2021, 12, 13))));
        Basket basket2 = new Basket(new BasketIdentifier("vision", 7, LocalDate.of(2021, 10, 10)));
        basket2.addProducts(UUID.randomUUID(), 1);
        basket2.addProducts(UUID.randomUUID(), 2);
        basket2.addProducts(UUID.randomUUID(), 3);
        basketRepository.save(basket2);

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
        SellerRepository sellerRepository = new SellerRepository(lastContext);
        BasketRepository basketRepository = new BasketRepository(lastContext);
        OfferRepository offerRepository = new OfferRepository(lastContext);
        InvoiceItemRepository invoiceItemRepository = new InvoiceItemRepository(lastContext);

        productRepository.findAll().forEach(System.out::println);
        invoiceRepository.findAll().forEach(System.out::println);
        buyerRepository.findAll().forEach(System.out::println);
        sellerRepository.findAll().forEach(System.out::println);
        basketRepository.findAll().forEach(System.out::println);
        offerRepository.findAll().forEach(System.out::println);
        invoiceItemRepository.findAll().forEach(System.out::println);

        lastContext.close();
    }

    private static void nextContext() {
        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");
    }
}
