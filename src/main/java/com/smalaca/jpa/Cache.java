package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.LoggingUtils;

public class Cache {

    private static Invoice invoice = null;

    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbPopulator.populateDb(entityManagerFactory);
            LoggingUtils.nextContext();
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoice1a = context.find(Invoice.class, DbPopulator.getInvoiceId());
                var invoice1b = context.find(Invoice.class, DbPopulator.getInvoiceId());
                System.out.println(invoice1a == invoice1b);
                invoice = invoice1a;
                var invoice1c = context
                        .createQuery("from Invoice i where i.id = :id", Invoice.class)
                        .setParameter("id", DbPopulator.getInvoiceId())
                        .setHint("org.hibernate.cacheable", true)
                        .getSingleResult();
                System.out.println(invoice1a == invoice1c);
                var invoice1d = context
                        .createQuery("from Invoice i where i.id = :id", Invoice.class)
                        .setParameter("id", DbPopulator.getInvoiceId())
                        .setHint("org.hibernate.cacheable", true)
                        .getSingleResult();
                System.out.println(invoice1a == invoice1d);
            });
            LoggingUtils.nextContext();
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoice2 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                System.out.println(invoice == invoice2);
            });
            LoggingUtils.nextContext();
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                System.out.println("In cache: " + entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId()));
                var invoice3 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                System.out.println(invoice == invoice3);
                entityManagerFactory.getCache().evictAll();
                System.out.println("In cache: " + entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId()));
                var invoice4 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                System.out.println(invoice == invoice4);
                System.out.println("In cache: " + entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId()));
            });
        });
    }
}
