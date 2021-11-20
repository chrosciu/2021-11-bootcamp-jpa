package eu.chrost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;
import java.util.function.Consumer;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        UUID id = UUID.randomUUID();

        runWithEntityManager(entityManagerFactory, entityManager -> {
            ProductRepository productRepository = new ProductRepository(entityManager);
            productRepository.save(new Product(id, "Water", "The best thing to drink"));

            Product found = productRepository.findById(id);
            System.out.println(found);
        });

        runWithEntityManager(entityManagerFactory, entityManager -> {
            ProductRepository productRepository = new ProductRepository(entityManager);
            System.out.println(productRepository.findById(id));
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
