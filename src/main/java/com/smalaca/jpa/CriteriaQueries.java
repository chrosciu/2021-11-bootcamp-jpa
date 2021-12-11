package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceItem_;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.domain.Invoice_;
import com.smalaca.jpa.dto.IdAndCount;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.LoggingUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


public class CriteriaQueries {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbPopulator.populateDb(entityManagerFactory);
            LoggingUtils.nextContext();

            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                //allInvoices(context);
                //allInvoicesIds(context);
                //allInvoicesIdsWithStatuses(context);
                //allInvoicesWithStatusCreated(context);
                //allInvoiceItems(context);
                allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan(context, InvoiceStatus.CREATED, 5);
                allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan(context, null, null);
            });
        });
    }

    private static void allInvoices(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root); //optional - works without it
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices: " + result);
    }

    private static void allInvoicesIds(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(UUID.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root.get(Invoice_.id));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices ids: " + result);
    }

    private static void allInvoicesIdsWithStatuses(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(IdAndStatus.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.multiselect(root.get("id"), root.get("status"));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices ids: " + result);
    }

    private static void allInvoicesWithStatusCreated(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("status"), InvoiceStatus.CREATED));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices with status created: " + result);
    }

    private static void allInvoiceItems(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(IdAndCount.class);
        var root = criteriaQuery.from(Invoice.class);
        var join = root.join(Invoice_.invoiceItems, JoinType.LEFT);
        criteriaQuery
                .multiselect(root.get(Invoice_.id), criteriaBuilder.count(join))
                .groupBy(root.get(Invoice_.id));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("allInvoiceItems: " + result);
    }

    private static void allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan(
            EntityManager context, InvoiceStatus status, Integer minAmount) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
        var root = criteriaQuery.from(Invoice.class);
        var joinInvoiceItem = root.join(Invoice_.invoiceItems);
        var joinProduct = joinInvoiceItem.join(InvoiceItem_.product);

        var predicates = new ArrayList<Predicate>();
        Optional.ofNullable(status).ifPresent(s -> predicates.add(criteriaBuilder.equal(root.get(Invoice_.status), s)));
        Optional.ofNullable(minAmount).ifPresent(m -> predicates.add(criteriaBuilder.greaterThan(joinInvoiceItem.get(InvoiceItem_.amount), m)));
        var predicatesArray = predicates.toArray(new Predicate[0]);

        criteriaQuery
                .select(joinInvoiceItem)
                .where(criteriaBuilder.and(predicatesArray));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan: " + result);
    }


}
