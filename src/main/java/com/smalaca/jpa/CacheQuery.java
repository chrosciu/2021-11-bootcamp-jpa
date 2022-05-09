package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

public class CacheQuery {
    private static final String CACHEABLE_HINT = "org.hibernate.cacheable";

    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoices1 = context
                        .createQuery("from Invoice i where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED", Invoice.class)
                        .setHint(CACHEABLE_HINT, true)
                        .getResultList();
            });
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoices2 = context
                        .createQuery("from Invoice i where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED", Invoice.class)
                        .setHint(CACHEABLE_HINT, true)
                        .getResultList();
            });
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoices3 = context
                        .createNativeQuery("select * from invoice i where i.status = 'CREATED'", Invoice.class)
                        .setHint(CACHEABLE_HINT, true)
                        .getResultList();
            });
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoices4 = context
                        .createNativeQuery("select * from invoice i where i.status = 'CREATED'", Invoice.class)
                        .setHint(CACHEABLE_HINT, true)
                        .getResultList();
            });
        });
    }
}
