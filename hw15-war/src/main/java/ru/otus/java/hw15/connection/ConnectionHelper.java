package ru.otus.java.hw15.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private ConnectionHelper() {}
    public static Connection getPostgresqlConnection() throws SQLException {

        final String connString = "jdbc:postgresql://" +    // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "hw15-war?" +                         // db name
                "user=hw12webserver&" +                     // login
                "password=hw12webserver";                   // password

        return DriverManager.getConnection(connString);
    }
}
