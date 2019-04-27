package ru.otus.java.hw10.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getPostgresqlConnection() throws SQLException {

        final String connString = "jdbc:postgresql://" +    // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "hw10-orm?" +                                   // db name
                "user=hw10orm&" +                         // login
                "password=hw10orm";                   // password

        return DriverManager.getConnection(connString);
    }

    public Connection getMysqlConnection() throws SQLException {

        final String connString = "jdbc:mysql://" +         // db type
                "localhost:" +                              // host name
                "3306/" +                                   // port
                "otus?" +                                   // db name
                "user=otus_user&" +                         // login
                "password=otus_password&" +                 // password
                "useSSL=false";                             // do not use Secure Sockets Layer

        return DriverManager.getConnection(connString);
    }
}
