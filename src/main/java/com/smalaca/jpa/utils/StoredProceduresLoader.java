package com.smalaca.jpa.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManagerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StoredProceduresLoader {
    private static final String DROP_PROCEDURE = "DROP ALIAS IF EXISTS GET_INVOICE_ITEMS_COUNT";

    private static final String CREATE_PROCEDURE = "CREATE ALIAS GET_INVOICE_ITEMS_COUNT AS  $$\n" +
            "import java.sql.Connection;\n" +
            "import java.sql.PreparedStatement;\n" +
            "import java.sql.ResultSet;\n" +
            "import java.sql.SQLException;\n" +
            "@CODE\n" +
            "int getOrdersWithValidItems(final Connection conn, final int minAmount) throws SQLException {\n" +
            "  PreparedStatement ps = conn.prepareStatement(\"SELECT COUNT(*) FROM INVOICEITEM WHERE AMOUNT >= ?\");\n" +
            "  ps.setInt(1, minAmount);\n" +
            "  ResultSet resultSet = ps.executeQuery();\n" +
            "  resultSet.next();\n" +
            "  return resultSet.getInt(1);\n" +
            "}\n" +
            "$$;";

    public static void load(EntityManagerFactory entityManagerFactory) {
        DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
            context.getTransaction().begin();
            var dropQuery = context.createNativeQuery(DROP_PROCEDURE);
            dropQuery.executeUpdate();
            var createQuery = context.createNativeQuery(CREATE_PROCEDURE);
            createQuery.executeUpdate();
            context.getTransaction().commit();
        });
    }
}
