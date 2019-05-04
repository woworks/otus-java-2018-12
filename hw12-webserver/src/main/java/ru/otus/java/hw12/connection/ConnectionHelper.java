package ru.otus.java.hw12.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private ConnectionHelper() {}
    public static Connection getPostgresqlConnection() throws SQLException {

        final String connString = "jdbc:postgresql://" +    // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "hw11-hibernate?" +                         // db name
                "user=hw11hibernate&" +                     // login
                "password=hw11hibernate";                   // password

        return DriverManager.getConnection(connString);
    }
}
