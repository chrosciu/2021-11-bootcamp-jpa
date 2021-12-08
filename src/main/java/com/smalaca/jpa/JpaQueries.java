package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.dto.IdWithStatus;
import com.smalaca.jpa.utils.DbUtils;

public class JpaQueries {
    public static void main(String[] args) {
        DbUtils.init();
        DbUtils.populateDb();
        DbUtils.nextContext();
        DbUtils.runInEntityManagerContext(context -> {

            System.out.println("Invoice1: " + context.find(Invoice.class, DbUtils.getInvoiceId()));

            System.out.println("All invoices: "
                    + context.createQuery("from Invoice i", Invoice.class).getResultList());

            System.out.println("All invoices statuses: "
                    + context.createQuery("select i.status from Invoice i").getResultList());

            System.out.println("All invoices ids and statuses: "
                    + context.createQuery("select i.id, i.status from Invoice i").getResultList());

            System.out.println("All wrapped invoices ids and statuses: "
                    + context.createQuery("select new com.smalaca.jpa.dto.IdWithStatus(i.id, i.status) from Invoice i", IdWithStatus.class).getResultList());

            System.out.println("Invoices count: "
                    + context.createQuery("select count(i) from Invoice i", Long.class).getSingleResult());

            System.out.println("Invoices count grouped by buyer: "
                    + context.createQuery("select new com.smalaca.jpa.dto.CountWithLogin(count (i), i.buyer.contactDetails.login) from Invoice i group by i.buyer").getResultList());

//            var missingJoinQuery = context
//                    .createQuery("select i.invoiceItems from Invoice i where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED and i.invoiceItems.amount > 5",
//                            InvoiceItem.class);
            var properJoinQuery = context
                    .createQuery("select ii from Invoice i join i.invoiceItems ii where i.status = com.smalaca.jpa.domain.InvoiceStatus.CREATED and ii.amount > 5",
                            InvoiceItem.class);
            System.out.println("Invoice items for invoices where amount > 5: " + properJoinQuery.getResultList());

            var namedParamQuery = context.createQuery("from InvoiceItem ii where ii.amount > :amount", InvoiceItem.class);
            namedParamQuery.setParameter("amount", 3);
            System.out.println(namedParamQuery.getResultList());

            var indexedParamQuery = context.createQuery("from InvoiceItem ii where ii.amount <= ?1", InvoiceItem.class);
            indexedParamQuery.setParameter(1, 3);
            System.out.println(indexedParamQuery.getResultList());

            var namedQuery = context.createNamedQuery("Invoice.findBySellerLogin", Invoice.class);
            namedQuery.setParameter("login", "natasha");
            System.out.println(namedQuery.getResultList());

            context.getTransaction().begin();
            var updateQuery = context.createQuery("update InvoiceItem i set i.amount = i.amount * 2 where i.amount > 1");
            var updateResult = updateQuery.executeUpdate();
            System.out.println("Updated rows: " + updateResult);
            context.getTransaction().commit();

        });

        DbUtils.close();
    }


}
