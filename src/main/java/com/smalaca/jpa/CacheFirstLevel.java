package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

public class CacheFirstLevel {
    private static Invoice invoice;

    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoice1 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                var invoice2 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                assert invoice1 == invoice2 : "entities with the same id should be equal within the context";
                invoice = invoice1;
            });
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var invoice3 = context.find(Invoice.class, DbPopulator.getInvoiceId());
                assert invoice != invoice3 : "entities with the same id should not be equal outside the context";
            });
        });
    }
}
