package com.smalaca.jpa;

import com.smalaca.jpa.domain.Buyer_;
import com.smalaca.jpa.domain.ContactDetails_;
import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.domain.Invoice_;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriteriaQueries {
    private static class AllInvoices {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, entityManager -> {

                    var cb = entityManager.getCriteriaBuilder();
                    var cq = cb.createQuery(Invoice.class);
                    cq.from(Invoice.class);
                    var query = entityManager.createQuery(cq);

//                    var query = entityManager.createQuery("from Invoice");

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
                DbUtils.runInEntityManagerContext(entityManagerFactory, entityManager -> {
                    var cb = entityManager.getCriteriaBuilder();
                    var cq = cb.createQuery(UUID.class);
                    cq.select(cq.from(Invoice.class).get(Invoice_.id));

//                    var query = entityManager.createQuery("select i.id from Invoice i");

                    var query = entityManager.createQuery(cq);
                    var result = query.getResultList();
                    System.out.println("All invoices ids: " + result);
                });
            });
        }
    }

    private static class AllInvoicesWithGivenStatus {
        static InvoiceStatus invoiceStatusToBeSearched = InvoiceStatus.CREATED;
        //static InvoiceStatus invoiceStatusToBeSearched = null;

        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, entityManager -> {
                    var cb = entityManager.getCriteriaBuilder();
                    var cq = cb.createQuery(Invoice.class);
                    //from Invoice i
                    var root = cq.from(Invoice.class);

                    List<Predicate> conditions = new ArrayList<>();
                    if (invoiceStatusToBeSearched != null) {
                        //i.status = CREATED
                        conditions.add(cb.equal(root.get(Invoice_.status), invoiceStatusToBeSearched));
                    }
                    Predicate[] conditionsArray = conditions.toArray(new Predicate[0]);
                    cq.select(root).where(conditionsArray);

                    var query = entityManager.createQuery(cq);
                    var result = query.getResultList();
                    System.out.println("All invoices with given status: " + result);
                });
            });
        }
    }

    private static class AllInvoicesWithBuyerLogin {
        static String buyerLogin = "carol.d4nv3rs";

        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, entityManager -> {
                    var cb = entityManager.getCriteriaBuilder();
                    var cq = cb.createQuery(Invoice.class);
                    //from Invoice i
                    var root = cq.from(Invoice.class);
                    var condition = cb.equal(
                            //i.buyer.contactDetails.login = :buyerLogin
                            root.get(Invoice_.buyer).get(Buyer_.contactDetails).get(ContactDetails_.login), buyerLogin);

                    cq.select(root).where(condition);

                    var query = entityManager.createQuery(cq);
                    var result = query.getResultList();
                    System.out.println("All invoices with buyer login: " + result);
                });
            });
        }
    }

    private static class AllInvoiceItemsWithStatusCreated {
        public static void main(String[] args) {
            DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
                DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
                DbUtils.runInEntityManagerContext(entityManagerFactory, entityManager -> {
                    var cb = entityManager.getCriteriaBuilder();
                    var cq = cb.createQuery(InvoiceItem.class);
                    //from Invoice i
                    var root = cq.from(Invoice.class);
                    //i join i.invoiceItems ii
                    var join = root.join(Invoice_.invoiceItems);
                    //select ii
                    cq.select(join);
                    //where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED
                    cq.where(cb.equal(root.get(Invoice_.status), InvoiceStatus.CREATED));
                    var query = entityManager.createQuery(cq);

//                    var query = entityManager.createQuery(
//                            "select ii from Invoice i join i.invoiceItems ii where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED",
//                            InvoiceItem.class);
                    var result = query.getResultList();
                    System.out.println("All invoice items with status CREATED: " + result);
                });
            });
        }
    }
}
