package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

public class NativeQueries {
    private static class AllInvoices {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select * from invoice";
                    var query = context.createNativeQuery(queryString, Invoice.class);
                    var result = query.getResultList();
                    System.out.println("All invoices: " + result);
                });
            });
        }
    }

    private static class AllInvoicesIdsAndStatuses {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var query = context.createNativeQuery("select id, status from invoice");
                    var result = query.getResultList();
                    var resultTransformed = result.stream()
                            .map((Function<Object[], IdAndStatus>) row -> new IdAndStatus((byte[])(row[0]), (String)(row[1])))
                            .collect(Collectors.toList());
                    System.out.println("All invoices id and statuses: " + resultTransformed);
                });
            });
        }
    }

    private static class AllInvoicesIdsAndStatusesWithMapping {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var query = context.createNativeQuery("select id, status from invoice", "idAndStatus");
                    var result = query.getResultList();
                    System.out.println("All invoices id and statuses with mapping: " + result);
                });
            });
        }
    }

    private static class AllInvoicesCount {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select count(*) from invoice";
                    var query = context.createNativeQuery(queryString);
                    var result = query.getSingleResult();
                    System.out.println("All invoices count: " + result);
                });
            });
        }
    }

    private static class AllInvoiceItemsWithMinAmount {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var minAmount = 5;
                    var queryString = "select * from invoiceitem where amount >= :minAmount";
                    //var queryString = "select * from invoiceitem where amount >= ?1";
                    var query = context.createNativeQuery(queryString, InvoiceItem.class);
                    query.setParameter("minAmount", minAmount);
                    //query.setParameter(1, minAmount);
                    var result = query.getResultList();
                    System.out.println("All invoices items with min amount " + result);
                });
            });
        }
    }

    private static class DoubleInvoiceItemsAmount {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "update invoiceitem set amount = amount * 2 where amount > 1";
                    var query = context.createNativeQuery(queryString);
                    context.getTransaction().begin();
                    var result = query.executeUpdate();
                    context.getTransaction().commit();
                    System.out.println("Updated rows: " + result);
                });
            });
        }
    }

    private static class AllInvoicesForSellerLogin {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var login = "natasha";
                    var query = context.createNamedQuery("Invoice.findBySellerLoginNative", Invoice.class);
                    query.setParameter("login", login);
                    var result = query.getResultList();
                    System.out.println("All invoices for seller login: " + result);
                });
            });
        }
    }
}
