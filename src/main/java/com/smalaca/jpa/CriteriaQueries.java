package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceItem_;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.domain.Invoice_;
import com.smalaca.jpa.dto.CountWithStatus;
import com.smalaca.jpa.dto.IdWithStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.LoggingUtils;

import javax.persistence.EntityManager;
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
                allInvoices(context);
                allInvoicesIds(context);
                allInvoicesIdsWithStatuses(context);
                allInvoicesWithStatusCreated(context);
                allInvoicesGroupedByStatus(context);
                allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan(context, InvoiceStatus.CREATED, 5);
                allInvoicesWithFetchJoin(context);
                doubleInvoiceItemsAmount(context);
            });
        });
    }

    private static void allInvoices(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root);
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices: " + result);
    }

    private static void allInvoicesIds(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(UUID.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root.get("id"));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices ids: " + result);
    }

    private static void allInvoicesIdsWithStatuses(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(IdWithStatus.class);
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

    private static void allInvoicesGroupedByStatus(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(CountWithStatus.class);
        var root = criteriaQuery.from(Invoice.class);
        criteriaQuery.multiselect(criteriaBuilder.count(root), root.get("status")).groupBy(root.get("status"));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("Invoices grouped by status: " + result);
    }

    private static void allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan(
            EntityManager context, InvoiceStatus status, Integer minAmount) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
        var root = criteriaQuery.from(Invoice.class);
        var join = root.join(Invoice_.invoiceItems);
        var predicates = new ArrayList<Predicate>();
        Optional.ofNullable(status).ifPresent(s -> predicates.add(criteriaBuilder.equal(root.get(Invoice_.status), s)));
        Optional.ofNullable(minAmount).ifPresent(m -> predicates.add(criteriaBuilder.greaterThan(join.get(InvoiceItem_.amount), m)));
        criteriaQuery
                .select(join)
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan: " + result);
    }

    private static void allInvoicesWithFetchJoin(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        var root = criteriaQuery.from(Invoice.class);
        root.fetch("invoiceItems");
        root.fetch("offer");
        root.fetch("seller");
        root.fetch("buyer");
        criteriaQuery.select(root);
        var query = context.createQuery(criteriaQuery);
        var result = query.getResultList();
        System.out.println("All invoices with fetch join: " + result);
    }

    private static void doubleInvoiceItemsAmount(EntityManager context) {
        var criteriaBuilder = context.getCriteriaBuilder();
        var criteriaUpdate = criteriaBuilder.createCriteriaUpdate(InvoiceItem.class);
        var root = criteriaUpdate.from(InvoiceItem.class);
        criteriaUpdate.set(root.get(InvoiceItem_.amount), criteriaBuilder.prod(root.get(InvoiceItem_.amount), 2));
        var query = context.createQuery(criteriaUpdate);
        context.getTransaction().begin();
        var result = query.executeUpdate();
        context.getTransaction().commit();
        System.out.println("Number of updated invoice items: " + result);
    }

}
