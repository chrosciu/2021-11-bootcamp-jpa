package eu.chrost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class JpaDemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        AtomicReference<UUID> toRemoveId = new AtomicReference<>();
        AtomicReference<UUID> toModifyId = new AtomicReference<>();

        runWithEntityManager(entityManagerFactory, entityManager -> {
            ProductRepository productRepository = new ProductRepository(entityManager);
            productRepository.save(new Product("Water", "The best thing to drink"));
            toRemoveId.set(productRepository.save(new Product("Bread", "Something to start with")));
            toModifyId.set(productRepository.save(new Product("Carrot", "Because they said you will see better")));

            Product productWithCategories = new Product("Box", "Bigger than you expect");
            productWithCategories.addCategory("boxes");
            productWithCategories.addCategory("stuff");
            productRepository.save(productWithCategories);

            InvoiceRepository invoiceRepository = new InvoiceRepository(entityManager);
            invoiceRepository.save(Invoice.created());
            invoiceRepository.save(Invoice.created());
            invoiceRepository.save(Invoice.created());

            BuyerRepository buyerRepository = new BuyerRepository(entityManager);
            buyerRepository.save(new Buyer(new ContactDetails("pparker", "111111111", "peter.parker@marvel.com")));
            buyerRepository.save(new Buyer(new ContactDetails("srogers", "123456789", "captain.america@marvel.com")));
            buyerRepository.save(new Buyer(new ContactDetails("carol.d4nv3rs", "987654321", "captain@marvel.com")));

            BasketRepository basketRepository = new BasketRepository(entityManager);
            Basket basket1 = new Basket(new BasketIdentifier("smalaca", 13, LocalDate.of(2021, 1, 20)));
            basket1.addProducts(UUID.randomUUID(), 13);
            basket1.addProducts(UUID.randomUUID(), 42);
            basket1.addProducts(UUID.randomUUID(), 1);
            basket1.addProducts(toModifyId.get(), 1);
            basketRepository.save(basket1);
            basketRepository.save(new Basket(new BasketIdentifier("hawkeye", 42, LocalDate.of(2021, 12, 13))));
            Basket basket2 = new Basket(new BasketIdentifier("vision", 7, LocalDate.of(2021, 10, 10)));
            basket2.addProducts(UUID.randomUUID(), 1);
            basket2.addProducts(UUID.randomUUID(), 2);
            basket2.addProducts(UUID.randomUUID(), 3);
            basket2.addProducts(toModifyId.get(), 1);
            basketRepository.save(basket2);
        });

        runWithEntityManager(entityManagerFactory, entityManager -> {
            ProductRepository productRepository = new ProductRepository(entityManager);

            productRepository.removeById(toRemoveId.get());
        });

        runWithEntityManager(entityManagerFactory, entityManager -> {
            ProductRepository productRepository = new ProductRepository(entityManager);

            Product toModify = productRepository.findById(toModifyId.get());

            toModify.changeDescriptionTo("Just carrot");
            productRepository.update(toModify);
        });

        runWithEntityManager(entityManagerFactory, entityManager -> {
            ProductRepository productRepository = new ProductRepository(entityManager);
            InvoiceRepository invoiceRepository = new InvoiceRepository(entityManager);
            BuyerRepository buyerRepository = new BuyerRepository(entityManager);

            productRepository.findAll().forEach(System.out::println);
            invoiceRepository.findAll().forEach(System.out::println);
            buyerRepository.findAll().forEach(System.out::println);
        });
    }

    private static void runWithEntityManager(EntityManagerFactory entityManagerFactory, Consumer<EntityManager> action) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            action.accept(entityManager);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
