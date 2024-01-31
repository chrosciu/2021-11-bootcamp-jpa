package eu.chrost;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class ProductRepository {
    private final EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        return product.getId();
    }

    public Product findById(UUID id) {
        return entityManager.find(Product.class, id);
    }

    public void removeById(UUID id) {
        Product found = findById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(found);
        entityManager.getTransaction().commit();
    }

    public List<Product> findAll() {
        return entityManager
                .createQuery("FROM Product", Product.class)
                .getResultList();
    }

    public void update(Product product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }
}
