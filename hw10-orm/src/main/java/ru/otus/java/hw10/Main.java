package ru.otus.java.hw10;


import ru.otus.java.hw10.connection.ConnectionHelper;
import ru.otus.java.hw10.entity.UserDataSet;
import ru.otus.java.hw10.executors.EntityExecutor;
import ru.otus.java.hw10.executors.OrmEntityExecutor;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserDataSet user = new UserDataSet("John Doe", 22);
        EntityExecutor executor = new OrmEntityExecutor(ConnectionHelper.getPostgresqlConnection());
        long updatedUserId =  executor.save(user);
        System.out.println("updatedUserId = " + updatedUserId);


        UserDataSet userFromDB = executor.load(updatedUserId, UserDataSet.class);
        System.out.println("userFromDB = " + userFromDB);

    }
}
