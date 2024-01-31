package eu.chrost;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class InvoiceRepository {
    private final EntityManager entityManager;

    public InvoiceRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Invoice invoice) {
        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();
        return invoice.getId();
    }

    public List<Invoice> findAll() {
        return entityManager
                .createQuery("FROM Invoice", Invoice.class)
                .getResultList();
    }

    public void removeById(UUID id) {
        Invoice found = entityManager.find(Invoice.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(found);
        entityManager.getTransaction().commit();
    }
}
