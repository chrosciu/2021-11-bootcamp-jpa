package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceItem_;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.domain.Invoice_;
import com.smalaca.jpa.dto.IdAndCount;
import com.smalaca.jpa.dto.IdAndStatus;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

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
                    criteriaQuery.select(root); //opcjonalnie
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
                    criteriaQuery.select(root);
                    var statusPredicate = criteriaBuilder.equal(root.get(Invoice_.status), InvoiceStatus.CREATED);
                    var idPredicate = criteriaBuilder.equal(root.get(Invoice_.id), UUID.randomUUID());
                    criteriaQuery.where(criteriaBuilder.or(statusPredicate, idPredicate));
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
                    criteriaQuery.select(join);
                    var statusPredicate = criteriaBuilder.equal(root.get(Invoice_.status), InvoiceStatus.CREATED);
                    criteriaQuery.where(statusPredicate);
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
            InvoiceStatus status = InvoiceStatus.CREATED;
            Integer minAmount = 5;
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                    var criteriaBuilder = context.getCriteriaBuilder();
                    var criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
                    var root = criteriaQuery.from(Invoice.class);
                    var join = root.join(Invoice_.invoiceItems);

                    var predicates = new ArrayList<Predicate>();
                    Optional.ofNullable(status).ifPresent(
                            s -> predicates.add(criteriaBuilder.equal(root.get(Invoice_.status), s)));
                    Optional.ofNullable(minAmount).ifPresent(
                            m -> predicates.add(criteriaBuilder.greaterThan(join.get(InvoiceItem_.amount), m)));
                    var predicatesArray = predicates.toArray(new Predicate[0]);

                    criteriaQuery
                            .select(join)
                            .where(predicatesArray);
                    var query = context.createQuery(criteriaQuery);
                    var result = query.getResultList();
                    System.out.println("allInvoiceItemsForInvoicesWithGivenStatusAndAmountGreaterThan: " + result);
                });
            });
        }
    }
}
