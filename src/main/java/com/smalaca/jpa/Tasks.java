package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;
import lombok.NonNull;
import lombok.Value;

import javax.persistence.EntityManager;
import java.util.List;

public class Tasks {
    @Value
    static class SearchParams {
        @NonNull
        InvoiceStatus invoiceStatus;
        @NonNull
        String sellerLogin;
    }

    static List<Invoice> findInvoicesByParams(EntityManager entityManager, SearchParams searchParams) {
        var queryString = "TODO"; //build query string from searchParams
        var query = entityManager.createQuery(queryString, Invoice.class);
        return query.getResultList();
    }

    public static void main(String[] args) {
        SearchParams searchParams = new SearchParams(InvoiceStatus.CREATED, "natasha");

        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
            DbUtils.runInEntityManagerContext(entityManagerFactory, entityManager -> {
                List<Invoice> invoices = findInvoicesByParams(entityManager, searchParams);
                System.out.println(invoices);
            });
        });
    }
}
