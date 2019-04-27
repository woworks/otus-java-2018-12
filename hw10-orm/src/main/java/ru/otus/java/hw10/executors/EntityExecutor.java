package ru.otus.java.hw10.executors;

import ru.otus.java.hw10.entity.DataSet;

import java.sql.SQLException;


public interface EntityExecutor {

    <T extends DataSet> long save(T dataSet) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz);
}
