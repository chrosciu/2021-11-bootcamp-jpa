package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

public class JpaQueries {
    private static class DisplayAll {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::displayAll);
            });
        }
    }

    private static class InvoiceById {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var invoice = context.find(Invoice.class, DbPopulator.getInvoiceId());
                    System.out.println("Invoice by id(" + DbPopulator.getInvoiceId() + ") : " + invoice);
                });
            });
        }
    }

    private static class AllInvoices {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "from Invoice i";
                    var query = context.createQuery(queryString, Invoice.class);
                    var result = query.getResultList();
                    System.out.println("All invoices: " + result);
                });
            });
        }
    }

    private static class AllInvoicesStatuses {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i.status from Invoice i";
                    var query = context.createQuery(queryString, InvoiceStatus.class);
                    var result = query.getResultList();
                    System.out.println("All invoices statuses: " + result);
                });
            });
        }
    }
}
