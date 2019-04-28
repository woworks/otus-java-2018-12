package ru.otus.java.hw10.executors;

import ru.otus.java.hw10.entity.DataSet;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Collection;

class Executor {

    private Executor(){}

    static <T extends DataSet> T query(Connection connection, long id, Class<T> clazz, String query, TResultHandler<T> handler) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            final ResultSet result = statement.executeQuery();

            return handler.handle(result, clazz);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new ExecutorException("Cannot execute query!");
        }
    }

    static void execute(Connection connection, String executeQuery) {
        try (final PreparedStatement statement = connection.prepareStatement(executeQuery)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExecutorException("Cannot execute execution query!");
        }
    }

    static long create(Connection connection, String update, Collection<Object> values) {
        try (final PreparedStatement statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            for (Object object: values){
                if (object.getClass().equals(String.class)){
                    statement.setString(i++, (String) object);
                } else if (object.getClass().equals(int.class) || object.getClass().equals(Integer.class)){
                    statement.setInt(i++, (Integer) object);
                } else {
                    i++;
                }
                // add all the other types
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new ExecutorException(e.getMessage());
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
