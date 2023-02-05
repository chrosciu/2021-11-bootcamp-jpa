package com.smalaca.jpa;

import com.smalaca.jpa.domain.Buyer;
import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.dto.BuyerAndCount;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.dto.LoginAndCount;
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

    private static class AllInvoices {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i from Invoice i";
                    var query = context.createQuery(queryString, Invoice.class);
                    var result = query.getResultList();
                    System.out.println("All invoices: " + result);
                    result.forEach(invoice -> {
                        System.out.println("" + invoice.getBuyer());
                    });
                });
            });
        }
    }

    private static class AllInvoicesWithJoinFetch {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i from Invoice i join fetch i.buyer b join fetch i.seller s join fetch i.offer o join fetch o.offerItems oi";
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

    private static class AllUniqueInvoicesStatuses {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select distinct (i.status) from Invoice i";
                    var query = context.createQuery(queryString, InvoiceStatus.class);
                    var result = query.getResultList();
                    System.out.println("All unique invoices statuses: " + result);
                });
            });
        }
    }

    private static class AllInvoicesIdAndStatuses {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i.id, i.status from Invoice i";
                    var query = context.createQuery(queryString, Object[].class);
                    var result = query.getResultList();
                    System.out.println("All invoices id and statuses: ");
                    result.forEach(array -> {
                        System.out.println(String.format("id: %s, status: %s", array[0], array[1]));
                    });
                });
            });
        }
    }

    private static class AllInvoicesIdAndStatusesAsWrapperObject {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select new com.smalaca.jpa.dto.IdAndStatus(i.id, i.status) from Invoice i";
                    var query = context.createQuery(queryString, IdAndStatus.class);
                    var result = query.getResultList();
                    System.out.println("All invoices id and statuses: " + result);
                });
            });
        }
    }

    private static class AllInvoicesCount {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select count(i) from Invoice i";
                    var query = context.createQuery(queryString, Long.class);
                    var result = query.getSingleResult();
                    System.out.println("All invoices count: " + result);
                });
            });
        }
    }

    private static class AllInvoicesWithStatusCreated {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select ii.amount from Invoice i join i.invoiceItems ii where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED";
                    var query = context.createQuery(queryString, Integer.class);
                    var result = query.getResultList();
                    System.out.println("All invoices with status CREATED: " + result);
                });
            });
        }
    }

    private static class AllInvoicesFromBuyer {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                Buyer buyer = DbPopulator.getCarol();
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i from Invoice i where i.buyer = :buyer";
                    var query = context.createQuery(queryString, Invoice.class);
                    query.setParameter("buyer", buyer);
                    var result = query.getResultList();
                    System.out.println("All invoices for buyer: " + result);
                });
            });
        }
    }

    private static class AllInvoicesFromBuyerWithNamedQuery {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                Buyer buyer = DbPopulator.getCarol();
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var query = context.createNamedQuery(Invoice.FIND_FOR_BUYER, Invoice.class);
                    query.setParameter("buyer", buyer);
                    var result = query.getResultList();
                    System.out.println("All invoices for buyer: " + result);
                });
            });
        }
    }

    private static class AllInvoicesFromCarolBuyerWithPositionParam {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i from Invoice i where i.buyer = ?1";
                    var query = context.createQuery(queryString, Invoice.class);
                    query.setParameter(1, DbPopulator.getCarol());
                    var result = query.getResultList();
                    System.out.println("All invoices with status CREATED: " + result);
                });
            });
        }
    }

    private static class AllBuyersLogins {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select distinct (i.buyer.contactDetails.login) from Invoice i";
                    var query = context.createQuery(queryString, String.class);
                    var result = query.getResultList();
                    System.out.println("All buyers logins: " + result);
                });
            });
        }
    }

    private static class AllInvoiceItemsAmountsWithStatusCreated {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select i.invoiceItems. from Invoice i where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED";
                    var query = context.createQuery(queryString, Integer.class);
                    var result = query.getResultList();
                    System.out.println("All invoice items amount with status CREATED: " + result);
                });
            });
        }
    }

    private static class AllInvoicesCountForBuyerLogin {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select new com.smalaca.jpa.dto.LoginAndCount(i.buyer.contactDetails.login, count (i)) " +
                            "from Invoice i group by i.buyer.contactDetails.login";
                    //works from Java 15 upwards
//                    var queryString = """
//                            select new com.smalaca.jpa.dto.LoginAndCount(i.buyer.contactDetails.login, count (i))
//                            from Invoice i
//                            group by i.buyer.contactDetails.login""";
                    var query = context.createQuery(queryString, LoginAndCount.class);
                    var result = query.getResultList();
                    System.out.println("All invoices count for buyer login: " + result);
                });
            });
        }
    }

    private static class AllInvoicesCountForBuyer {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "select new com.smalaca.jpa.dto.BuyerAndCount(i.buyer, count (i)) " +
                            "from Invoice i group by i.buyer";
                    var query = context.createQuery(queryString, BuyerAndCount.class);
                    var result = query.getResultList();
                    System.out.println("All invoices count for buyer: " + result);
                });
            });
        }
    }

    private static class DoubleInvoiceItemsAmount {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var queryString = "update InvoiceItem i set i.amount = i.amount * 2 where i.amount > 1";
                    var query = context.createQuery(queryString);
                    context.getTransaction().begin();
                    var result = query.executeUpdate();
                    context.getTransaction().commit();
                    System.out.println("Updated rows: " + result);
                });
            });
        }
    }


}
