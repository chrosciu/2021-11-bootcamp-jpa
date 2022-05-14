package com.smalaca.jpa;

import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.dto.InvoiceDto;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                System.out.println(findInvoicesWithSQL(context, "E", InvoiceStatus.CREATED));
            });
        });
    }

    private static List<InvoiceDto> findInvoicesWithSQL(EntityManager context, String offerNumberPattern, InvoiceStatus status) {
        var query = context.createNamedQuery("Invoice.findByOfferNumberOrStatus", InvoiceDto.class);
        query.setParameter("offerNumber", "%" + offerNumberPattern + "%");
        query.setParameter("status", status.name());
        return query.getResultList();
    }
}
