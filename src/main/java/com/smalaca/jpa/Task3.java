package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.domain.Invoice_;
import com.smalaca.jpa.domain.Offer_;
import com.smalaca.jpa.dto.InvoiceDto;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.LoggingUtils;
import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Task3 {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbPopulator.populateDb(entityManagerFactory);
            LoggingUtils.nextContext();
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                System.out.println(findInvoicesWithCriteria(context, "E", InvoiceStatus.CREATED));
            });
        });
    }

    private static List<InvoiceDto> findInvoicesWithCriteria(
            EntityManager context, String offerNumberPattern, @NonNull InvoiceStatus status) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(InvoiceDto.class);

        var root = criteriaQuery.from(Invoice.class);
        var joinOffers = root.join(Invoice_.offer);
        var joinOfferItems = joinOffers.join(Offer_.offerItems, JoinType.LEFT);

        var predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.equal(root.get(Invoice_.status), status));
        Optional.ofNullable(offerNumberPattern).ifPresent(p -> predicates.add(criteriaBuilder.like(joinOffers.get(Offer_.OFFER_NUMBER), "%"+p+"%")));
        var predicatesArray = predicates.toArray(new Predicate[0]);

        criteriaQuery
                .multiselect(root.get(Invoice_.id),joinOffers.get(Offer_.offerNumber),criteriaBuilder.count(joinOfferItems))
                .where(criteriaBuilder.or(predicatesArray))
                .groupBy(root.get(Invoice_.id));
        var query = context.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
