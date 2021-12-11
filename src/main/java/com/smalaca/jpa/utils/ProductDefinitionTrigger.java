package com.smalaca.jpa.utils;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductDefinitionTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        var name = (String)(newRow[1]);
        newRow[1] = name.toUpperCase();
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void remove() throws SQLException {
    }
}
