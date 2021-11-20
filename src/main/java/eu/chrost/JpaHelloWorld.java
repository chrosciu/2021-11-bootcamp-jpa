package eu.chrost;

import com.smalaca.jpa.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDo");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.persist(new Product(UUID.randomUUID(), "Water", "The best thing to drink"));

        entityManager.close();
    }
}
