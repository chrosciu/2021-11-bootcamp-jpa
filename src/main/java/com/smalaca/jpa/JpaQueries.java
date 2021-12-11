package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceStatus;
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
                allInvoicesStatuses(context);
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

}
