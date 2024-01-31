package eu.chrost;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class OfferRepository {
    private final EntityManager entityManager;

    public OfferRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UUID save(Offer offer) {
        entityManager.getTransaction().begin();
        entityManager.persist(offer);
        entityManager.getTransaction().commit();
        return offer.getId();
    }

    public List<Offer> findAll() {
        return entityManager.createQuery("SELECT i FROM Offer i").getResultList();
    }
}
