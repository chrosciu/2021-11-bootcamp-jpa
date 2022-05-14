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
        return null; //TODO
    }
}

