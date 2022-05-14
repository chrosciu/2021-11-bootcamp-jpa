package com.smalaca.jpa;

import com.smalaca.jpa.domain.InvoiceStatus;
import com.smalaca.jpa.dto.InvoiceDto;
import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbUtils.runInEntityManagerContext(entityManagerFactory, DbPopulator::populateDb);
            DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
                System.out.println(findInvoicesWithJPQL(context, "E", InvoiceStatus.CREATED));
            });
        });
    }

    private static List<InvoiceDto> findInvoicesWithJPQL(EntityManager context, String offerNumberPattern, InvoiceStatus status) {
        var queryString =
                "select new com.smalaca.jpa.dto.InvoiceDto(i.id, o.offerNumber, count(oi) ) " +
                        "from Invoice i " +
                        "join i.offer o left join o.offerItems oi " +
                        "where o.offerNumber like :pattern or i.status = :status " +
                        "group by i.id";

        var query= context.createQuery(queryString, InvoiceDto.class);
        query.setParameter("pattern","%" + offerNumberPattern + "%");
        query.setParameter("status", status);
        return query.getResultList();
    }
}

