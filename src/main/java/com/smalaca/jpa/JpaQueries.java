package com.smalaca.jpa;

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
}
