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

    private static final String DROP_TRIGGER = "DROP TRIGGER IF EXISTS PROD_DEF_INSERT";

    private static final String CREATE_TRIGGER = "CREATE TRIGGER PROD_DEF_INSERT BEFORE INSERT ON PRODUCTDEFINITION\n" +
            "    FOR EACH ROW CALL \"com.smalaca.jpa.utils.ProductDefinitionTrigger\"";

    public static void load(EntityManagerFactory entityManagerFactory) {
        DbUtils.runInEntityManagerContext(entityManagerFactory, context -> {
            context.getTransaction().begin();
            var dropQuery = context.createNativeQuery(DROP_PROCEDURE);
            dropQuery.executeUpdate();
            var createQuery = context.createNativeQuery(CREATE_PROCEDURE);
            createQuery.executeUpdate();
            var dropTriggerQuery = context.createNativeQuery(DROP_TRIGGER);
            dropTriggerQuery.executeUpdate();
            var createTriggerQuery = context.createNativeQuery(CREATE_TRIGGER);
            createTriggerQuery.executeUpdate();
            context.getTransaction().commit();
        });
    }
}
