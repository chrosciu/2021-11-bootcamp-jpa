package com.smalaca.jpa;

import com.smalaca.jpa.utils.DbPopulator;
import com.smalaca.jpa.utils.DbUtils;

public class JpaQueries {
    public static void main(String[] args) {
        DbUtils.runInEntityManagerFactory(entityManagerFactory -> {
            DbPopulator.populateDb(entityManagerFactory);
            DbPopulator.displayAll(entityManagerFactory);
        });
    }
}
