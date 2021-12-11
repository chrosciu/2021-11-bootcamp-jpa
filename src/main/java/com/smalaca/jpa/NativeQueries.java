package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.LoggingUtils;

import javax.persistence.EntityManager;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NativeQueries {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbPopulator.populateDb(entityManagerFactory);
            LoggingUtils.nextContext();

            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                //allInvoices(context);
                //allInvoicesIdsAndStatuses(context);
                //allInvoicesIdsAndStatusesWithMapping(context);
                //allInvoicesCount(context);
                //allInvoiceItemsWithMinAmount(context, 5);
                //allInvoicesForSellerLogin(context, "natasha");
                task2(context);
            });
        });
    }

    private static void allInvoices(EntityManager context) {
        var queryString = "select * from invoice";
        var query = context.createNativeQuery(queryString, Invoice.class);
        var result = query.getResultList();
        System.out.println("All invoices: " + result);
    }

    private static void allInvoicesIdsAndStatuses(EntityManager context) {
        var query = context.createNativeQuery("select id, status from invoice");
        var result = query.getResultList();
        var resultTransformed = result.stream()
                .map((Function<Object[], IdAndStatus>) row -> new IdAndStatus((byte[])(row[0]), (String)(row[1])))
                .collect(Collectors.toList());
        System.out.println(resultTransformed);
    }

    private static void allInvoicesIdsAndStatusesWithMapping(EntityManager context) {
        var query = context.createNativeQuery("select id, status from invoice", "idAndStatus");
        var result = query.getResultList();
        System.out.println(result);
    }

    private static void allInvoicesCount(EntityManager context) {
        var queryString = "select count(*) from invoice";
        var query = context.createNativeQuery(queryString);
        var result = query.getSingleResult();
        System.out.println("All invoices count: " + result);
    }

    private static void allInvoiceItemsWithMinAmount(EntityManager context, int minAmount) {
        var queryString = "select * from invoiceitem where amount >= :minAmount";
        //var queryString = "from InvoiceItem ii where ii.amount >= ?1";
        var query = context.createNativeQuery(queryString, InvoiceItem.class);
        query.setParameter("minAmount", minAmount);
        //query.setParameter(1, minAmount);
        var result = query.getResultList();
        System.out.println("All invoices items with min amount " + result);
    }

    private static void allInvoicesForSellerLogin(EntityManager context, String login) {
        var query = context.createNamedQuery("Invoice.findBySellerLoginNative", Invoice.class);
        query.setParameter("login", login);
        var result = query.getResultList();
        System.out.println("All invoices for seller login: " + result);
    }

    private static void task2(EntityManager context) {
        var queryString = "SELECT\n" +
                "    i.id, o.OFFERNUMBER, count(oi.ID)\n" +
                "from\n" +
                "    invoice i LEFT JOIN offer o ON i.offer_id = o.id\n" +
                "    LEFT JOIN OFFER_OFFERITEM o_oi ON o.id = o_oi.OFFER_ID\n" +
                "    LEFT JOIN OFFERITEM oi ON o_oi.OFFERITEMS_ID = oi.ID\n" +
                "WHERE o.OFFERNUMBER like 'e%' OR i.STATUS = 'CREATED'\n" +
                "GROUP BY i.id, o.OFFERNUMBER";
        var query = context.createNativeQuery(queryString);
        var result = query.getResultList();
        System.out.println(result);
    }
}
