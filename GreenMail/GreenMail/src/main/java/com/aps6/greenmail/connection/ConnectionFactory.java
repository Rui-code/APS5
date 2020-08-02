package com.aps6.greenmail.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:users.db");
        } catch (SQLException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
