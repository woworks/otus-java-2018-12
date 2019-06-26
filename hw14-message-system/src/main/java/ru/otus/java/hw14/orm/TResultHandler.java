package ru.otus.java.hw14.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface TResultHandler<T> {
    T handle(ResultSet resultSet, Class<T> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
