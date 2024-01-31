package eu.chrost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
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

            List<Product> products = productRepository.findAll();
            products.forEach(System.out::println);
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
