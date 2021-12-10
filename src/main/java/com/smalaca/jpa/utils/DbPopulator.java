package com.smalaca.jpa.utils;

import com.smalaca.jpa.domain.Basket;
import com.smalaca.jpa.domain.BasketIdentifier;
import com.smalaca.jpa.domain.BasketRepository;
import com.smalaca.jpa.domain.Buyer;
import com.smalaca.jpa.domain.BuyerRepository;
import com.smalaca.jpa.domain.Characteristic;
import com.smalaca.jpa.domain.ContactDetails;
import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceItemRepository;
import com.smalaca.jpa.domain.InvoiceRepository;
import com.smalaca.jpa.domain.Offer;
import com.smalaca.jpa.domain.OfferItem;
import com.smalaca.jpa.domain.OfferRepository;
import com.smalaca.jpa.domain.Product;
import com.smalaca.jpa.domain.ProductDefinition;
import com.smalaca.jpa.domain.ProductRepository;
import com.smalaca.jpa.domain.Rating;
import com.smalaca.jpa.domain.Seller;
import com.smalaca.jpa.domain.SellerRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DbPopulator {
    @Getter
    private static UUID invoiceId;

    public static void populateDb(EntityManagerFactory entityManagerFactory) {
        DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
            ProductRepository productRepository = new ProductRepository(context);

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

            Buyer carolDanvers = new Buyer(new ContactDetails("carol.d4nv3rs", "987654321", "captain@marvel.com"));
            Buyer peterParker = new Buyer(new ContactDetails("pparker", "111111111", "peter.parker@marvel.com"));
            BuyerRepository buyerRepository = new BuyerRepository(context);
            buyerRepository.save(peterParker);
            buyerRepository.save(new Buyer(new ContactDetails("srogers", "123456789", "captain.america@marvel.com")));
            buyerRepository.save(carolDanvers);

            Seller blackWidow = new Seller(new ContactDetails("natasha", "000111222", "romanoff@marvel.com"));
            Seller hawkeye = new Seller(new ContactDetails("hawk", "123123123", "eye@marvel.com"));
            SellerRepository sellerRepository = new SellerRepository(context);
            sellerRepository.save(blackWidow);
            sellerRepository.save(hawkeye);

            Seller withDefinitionsOne = new Seller(new ContactDetails("withDefinitionsOne", "1234", "seller1@mail.com"));
            ProductDefinition water = new ProductDefinition("water");
            water.add(Characteristic.COOL);
            water.add(Characteristic.SOFT);
            withDefinitionsOne.add(water);
            withDefinitionsOne.add(new ProductDefinition("bread"));

            Seller withDefinitionsTwo = new Seller(new ContactDetails("withDefinitionsTwo", "5678", "seller2@mail.com"));
            withDefinitionsTwo.add(new ProductDefinition("carrot"));
            sellerRepository.save(withDefinitionsOne);
            sellerRepository.save(withDefinitionsTwo);

            InvoiceRepository invoiceRepository = new InvoiceRepository(context);
            Invoice invoice1 = Invoice.created(carolDanvers, blackWidow);
            Invoice invoice2 = Invoice.created(carolDanvers, blackWidow);
            Invoice invoice3 = Invoice.created(peterParker, blackWidow);
            invoiceRepository.save(invoice1);
            invoiceRepository.save(invoice2);
            invoiceRepository.save(invoice3);

            InvoiceItemRepository invoiceItemRepository = new InvoiceItemRepository(context);
            invoiceItemRepository.save(new InvoiceItem(invoice1, productWithRatings, 13));
            invoiceItemRepository.save(new InvoiceItem(invoice1, productWithCategories, 7));

            OfferRepository offerRepository = new OfferRepository(context);
            Offer offer1 = new Offer("QWERTY");
            offerRepository.save(offer1);

            Offer offerWithItems = new Offer("ABCDEF");
            offerWithItems.add(new OfferItem(UUID.randomUUID(), 13));
            offerWithItems.add(new OfferItem(UUID.randomUUID(), 42));
            offerWithItems.add(new OfferItem(UUID.randomUUID(), 7));
            offerWithItems.add(new OfferItem(UUID.randomUUID(), 100));
            offerRepository.save(offerWithItems);

            invoice1.add(offer1);
            invoiceId = invoiceRepository.save(invoice1);

            invoice2.add(offerWithItems);
            invoiceRepository.save(invoice2);

            BasketRepository basketRepository = new BasketRepository(context);
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
        });
    }

    public static void displayAll(EntityManagerFactory entityManagerFactory) {
        DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
            ProductRepository productRepository = new ProductRepository(context);
            InvoiceRepository invoiceRepository = new InvoiceRepository(context);
            BuyerRepository buyerRepository = new BuyerRepository(context);
            SellerRepository sellerRepository = new SellerRepository(context);
            BasketRepository basketRepository = new BasketRepository(context);
            OfferRepository offerRepository = new OfferRepository(context);
            InvoiceItemRepository invoiceItemRepository = new InvoiceItemRepository(context);

            productRepository.findAll().forEach(System.out::println);
            invoiceRepository.findAll().forEach(System.out::println);
            buyerRepository.findAll().forEach(System.out::println);
            sellerRepository.findAll().forEach(System.out::println);
            basketRepository.findAll().forEach(System.out::println);
            offerRepository.findAll().forEach(System.out::println);
            invoiceItemRepository.findAll().forEach(System.out::println);
        });
    }
}
