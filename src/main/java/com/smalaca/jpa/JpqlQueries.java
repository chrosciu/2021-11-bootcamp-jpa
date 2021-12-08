package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.dto.IdWithStatus;
import com.smalaca.jpa.utils.DbUtils;

public class JpqlQueries {
    public static void main(String[] args) {
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
        });
    }


}
