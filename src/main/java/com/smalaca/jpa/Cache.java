package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.utils.DbUtils;



public class Cache {
    private static Invoice invoice = null;

    public static void main(String[] args) {
        DbUtils.init();
        DbUtils.populateDb();
        DbUtils.nextContext();
        DbUtils.runInEntityManagerContext(context -> {
            var invoice1 = context.find(Invoice.class, DbUtils.getInvoiceId());
            var invoice1a = context.find(Invoice.class, DbUtils.getInvoiceId());
            System.out.println(invoice1 == invoice1a);
            var invoice2 = context
                    .createQuery("from Invoice i where i.id = :id", Invoice.class)
                    .setParameter("id", DbUtils.getInvoiceId())
                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult();
            var invoice2a = context
                    .createQuery("from Invoice i where i.id = :id", Invoice.class)
                    .setParameter("id", DbUtils.getInvoiceId())
                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult();
            var invoice2b = context
                    .createNativeQuery("select * from invoice i where i.id = :id", Invoice.class)
                    .setParameter("id", DbUtils.getInvoiceId())
                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult();
            var invoice2c = context
                    .createNativeQuery("select * from invoice i where i.id = :id", Invoice.class)
                    .setParameter("id", DbUtils.getInvoiceId())
                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult();
            System.out.println(invoice2 == invoice2a);
            System.out.println(invoice2 == invoice2b);
            System.out.println(invoice2b == invoice2c);
            System.out.println(invoice1 == invoice2);
            invoice = invoice1;
        });
        DbUtils.nextContext();
        DbUtils.runInEntityManagerContext(context -> {
            var invoice3 = context.find(Invoice.class, DbUtils.getInvoiceId());
            System.out.println(invoice == invoice3);
            var invoice4 = context.find(Invoice.class, DbUtils.getInvoiceId());
        });
        DbUtils.nextContext();
        DbUtils.runInEntityManagerContext(context -> {
            System.out.println("In cache: " + DbUtils.getCache().contains(Invoice.class, DbUtils.getInvoiceId()));
            DbUtils.getCache().evictAll();
            System.out.println("In cache: " + DbUtils.getCache().contains(Invoice.class, DbUtils.getInvoiceId()));
            var invoice5 = context.find(Invoice.class, DbUtils.getInvoiceId());
            System.out.println("In cache: " + DbUtils.getCache().contains(Invoice.class, DbUtils.getInvoiceId()));
        });
        DbUtils.close();
    }
}
