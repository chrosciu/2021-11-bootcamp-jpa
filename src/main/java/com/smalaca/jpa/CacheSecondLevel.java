package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

public class CacheSecondLevel {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                assert entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
                entityManagerFactory.getCache().evictAll();
                assert !entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
            });
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                assert !entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
                var invoice = context.find(Invoice.class, DbPopulator.getInvoiceId());
                assert entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
            });
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                assert entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
                var invoice1 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                assert entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
                entityManagerFactory.getCache().evictAll();
                assert !entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
                var invoice2 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                //cache L2 sie nie odbuduje, gdyz invoice bylo juz w cache L1 !
                assert !entityManagerFactory.getCache().contains(Invoice.class, DbPopulator.getInvoiceId());
            });
        });
    }
}
