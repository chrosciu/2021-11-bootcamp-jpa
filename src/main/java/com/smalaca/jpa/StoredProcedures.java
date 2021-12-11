package com.smalaca.jpa;

import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import com.smalaca.jpa.utils.StoredProceduresLoader;

public class StoredProcedures {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            StoredProceduresLoader.load(entityManagerFactory);
            DbPopulator.populateDb(entityManagerFactory);
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                var nativeQuery = context.createNativeQuery("CALL GET_INVOICE_ITEMS_COUNT(:minAmount)");
                nativeQuery.setParameter("minAmount", 3);
                var result = (Integer) (nativeQuery.getSingleResult());
                System.out.println(result);
            });
        });
    }
}
