package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.dto.IdAndCount;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.dto.InvoiceDto;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.LoggingUtils;

import javax.persistence.EntityManager;

public class JpaQueries {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbPopulator.populateDb(entityManagerFactory);
            LoggingUtils.nextContext();
            //DbPopulator.displayAll(entityManagerFactory);

            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                //simpleQuery(context);
                //allInvoices(context);
                //allInvoicesStatuses(context);
                //allInvoicesIdWithStatuses(context);
                //allInvoicesCount(context);
                //allInvoicesWithStatusCreated(context);
                //allInvoiceItemsWithMinAmount(context, 5);
                //allBuyersLogins(context);
                //allInvoiceItemsAmountsWithStatusCreated(context);
                //allInvoicesIdsAndOffersCounts(context);
                //doubleInvoiceItems(context);
                //allInvoicesForSellerLogin(context, "natasha");
                invoicesWithFetch(context);
            });

        });
    }

    private static void simpleQuery(EntityManager context) {
        var invoice = context.find(Invoice.class, DbPopulator.getInvoiceId());
        System.out.println("Invoice 1: " + invoice);
    }

    private static void allInvoices(EntityManager context) {
        var queryString = "from Invoice i";
        var query = context.createQuery(queryString, Invoice.class);
        var result = query.getResultList();
        System.out.println("All invoices: " + result);
    }

    private static void allInvoicesStatuses(EntityManager context) {
        var queryString = "select i.status  from Invoice i";
        var query = context.createQuery(queryString, InvoiceStatus.class);
        var result = query.getResultList();
        System.out.println("All invoices statuses: " + result);
    }

    private static void allInvoicesIdWithStatuses(EntityManager context) {
        var queryString = "select new com.smalaca.jpa.dto.IdAndStatus(i.id, i.status) from Invoice i";
        var query = context.createQuery(queryString, IdAndStatus.class);
        var result = query.getResultList();
        System.out.println("All invoices id and statuses: " + result);
    }

    private static void allInvoicesCount(EntityManager context) {
        var queryString = "select count(i) from Invoice i";
        var query = context.createQuery(queryString, Long.class);
        var result = query.getSingleResult();
        System.out.println("All invoices count: " + result);
    }

    private static void allInvoicesWithStatusCreated(EntityManager context) {
        var queryString = "from Invoice i where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED";
        var query = context.createQuery(queryString, Invoice.class);
        var result = query.getResultList();
        System.out.println("All invoices with status CREATED: " + result);
    }

    private static void allInvoiceItemsWithMinAmount(EntityManager context, int minAmount) {
        //var queryString = "from InvoiceItem ii where ii.amount >= :minAmount";
        var queryString = "from InvoiceItem ii where ii.amount >= ?1";
        var query = context.createQuery(queryString, InvoiceItem.class);
        //query.setParameter("minAmount", minAmount);
        query.setParameter(1, minAmount);
        var result = query.getResultList();
        System.out.println("All invoices items with min amount " + result);
    }

    private static void allBuyersLogins(EntityManager context) {
        var queryString = "select distinct (i.buyer.contactDetails.login) from Invoice i";
        var query = context.createQuery(queryString, String.class);
        var result = query.getResultList();
        System.out.println("All buyers logins: " + result);
    }

    private static void allInvoiceItemsAmountsWithStatusCreated(EntityManager context) {
        var queryString = "select ii.amount from Invoice i join i.invoiceItems ii where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED";
        var query = context.createQuery(queryString, Integer.class);
        var result = query.getResultList();
        System.out.println("All invoice items amount with status CREATED: " + result);
    }

    private static void allInvoicesIdsAndOffersCounts(EntityManager context) {
        var queryString = "select new com.smalaca.jpa.dto.IdAndCount(i.id, count(o)) from Invoice i left join i.offer o group by i.id";
        var query = context.createQuery(queryString, IdAndCount.class);
        var result = query.getResultList();
        System.out.println("All invoice ids and offers counts: " + result);
    }

    private static void doubleInvoiceItems(EntityManager context) {
        var queryString = "update InvoiceItem i set i.amount = i.amount * 2 where i.amount > 1";
        var query = context.createQuery(queryString);
        context.getTransaction().begin();
        var result = query.executeUpdate();
        context.getTransaction().commit();
        System.out.println("Updated rows: " + result);
    }

    private static void allInvoicesForSellerLogin(EntityManager context, String login) {
        var query = context.createNamedQuery("Invoice.findBySellerLogin", Invoice.class);
        query.setParameter("login", login);
        var result = query.getResultList();
        System.out.println("All invoices for seller login: " + result);
    }

    private static void invoicesWithFetch(EntityManager context) {
        var queryString = "from Invoice i join fetch i.buyer b join fetch i.seller s join fetch i.offer o";
        var query = context.createQuery(queryString, Invoice.class);
        var result = query.getResultList();
        System.out.println("All invoices: " + result);
    }

}
