package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceItem_;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.domain.Invoice_;
import com.smalaca.jpa.domain.Offer;
import com.smalaca.jpa.domain.Offer_;
import com.smalaca.jpa.dto.IdAndCount;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class CriteriaQueries {
    private static class AllInvoices {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
                    var root = criteriaQuery.from(Invoice.class);
                    criteriaQuery.select(root); //działa i bez tego
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices: " + result);
                });
            });
        }
    }

    private static class AllInvoicesIds {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(UUID.class);
                    var root = criteriaQuery.from(Invoice.class);
                    criteriaQuery.select(root.get("id"));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices ids: " + result);
                });
            });
        }
    }

    private static class AllInvoicesIdsWithMetamodel {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(UUID.class);
                    var root = criteriaQuery.from(Invoice.class);
                    criteriaQuery.select(root.get(Invoice_.id));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices ids: " + result);
                });
            });
        }
    }

    private static class AllInvoicesIdsWithStatuses {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(IdAndStatus.class);
                    var root = criteriaQuery.from(Invoice.class);
                    criteriaQuery.multiselect(root.get(Invoice_.id), root.get(Invoice_.status));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices ids and statuses: " + result);
                });
            });
        }
    }

    private static class AllInvoicesWithStatusCreated {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
                    var root = criteriaQuery.from(Invoice.class);
                    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Invoice_.status), InvoiceStatus.CREATED));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices with status CREATED: " + result);
                });
            });
        }
    }

    private static class AllInvoiceItemsWithStatusCreated {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
                    var root = criteriaQuery.from(Invoice.class);
                    var join = root.join(Invoice_.invoiceItems);
                    criteriaQuery.select(join).where(criteriaBuilder.equal(root.get(Invoice_.status), InvoiceStatus.CREATED));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoice items with status CREATED: " + result);
                });
            });
        }
    }

    private static class AllInvoicesIdsAndInvoiceItemsCounts {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(IdAndCount.class);
                    var root = criteriaQuery.from(Invoice.class);
                    var join = root.join(Invoice_.invoiceItems, JoinType.LEFT);
                    criteriaQuery
                            .multiselect(root.get(Invoice_.id), criteriaBuilder.count(join))
                            .groupBy(root.get(Invoice_.id));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices ids and invoice items counts: " + result);
                });
            });
        }
    }

    private static class AllInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var status = InvoiceStatus.CREATED;
                    var minAmount = 5;

                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
                    var root = criteriaQuery.from(Invoice.class);
                    var join = root.join(Invoice_.invoiceItems);

                    var predicates = new ArrayList<Predicate>();
                    Optional.ofNullable(status).ifPresent(s -> predicates.add(criteriaBuilder.equal(root.get(Invoice_.status), s)));
                    Optional.ofNullable(minAmount).ifPresent(m -> predicates.add(criteriaBuilder.greaterThan(join.get(InvoiceItem_.amount), m)));
                    var predicatesArray = predicates.toArray(new Predicate[0]);

                    criteriaQuery
                            .select(join)
                            .where(criteriaBuilder.and(predicatesArray));
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan: " + result);
                });
            });
        }
    }

    private static class DoubleInvoiceItemsAmount {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaUpdate = criteriaBuilder.createCriteriaUpdate(InvoiceItem.class);
                    var root = criteriaUpdate.from(InvoiceItem.class);
                    criteriaUpdate.set(root.get(InvoiceItem_.amount), criteriaBuilder.prod(root.get(InvoiceItem_.amount), 2));
                    var query = context.createQuery(criteriaUpdate);
                    context.getTransaction().begin();
                    var result = query.executeUpdate();
                    context.getTransaction().commit();
                    System.out.println("Number of updated invoice items: " + result);
                });
            });
        }
    }

    private static class AllInvoicesWithJoinFetchForSellerBuyerAndOffer {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
                    var root = criteriaQuery.from(Invoice.class);
                    root.fetch(Invoice_.seller);
                    root.fetch(Invoice_.buyer);
                    var join = (Join<Invoice, Offer>)(root.fetch(Invoice_.offer));
                    join.fetch(Offer_.offerItems);
                    criteriaQuery.select(root);
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("All invoices: " + result);
                });
            });
        }
    }
}
