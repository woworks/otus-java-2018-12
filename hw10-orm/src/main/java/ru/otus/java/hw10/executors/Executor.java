package ru.otus.java.hw10.executors;

import ru.otus.java.hw10.entity.DataSet;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

class Executor {

    private Executor(){}

    static <T extends DataSet> T query(Connection connection, Class<T> clazz, String query, TResultHandler<T> handler) throws SQLException, ExecutorException {
        try (final Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            return handler.handle(result, clazz);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new ExecutorException("Cannot execute query!");
        }
    }

    static long update(Connection connection, String update) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            boolean hasResult = statement.execute(update);

            if (!hasResult) {
                throw new SQLException("Creating object failed, no rows affected.");
            }

            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

}
