package com.smalaca.jpa.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DbUtils {
    private static final String PERSISTENCE_UNIT_NAME = "ToDo";

    public static void runInEntityManagerFactory(Consumer<EntityManagerFactory> action) {
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            action.accept(entityManagerFactory);
        } finally {
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }

    public static void runInEntityManagerContext(EntityManagerFactory entityManagerFactory, Consumer<EntityManager> action) {
        EntityManager context = null;
        try {
            context = entityManagerFactory.createEntityManager();
            action.accept(context);
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

}

