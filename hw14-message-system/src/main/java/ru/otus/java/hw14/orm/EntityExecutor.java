package ru.otus.java.hw14.orm;

import ru.otus.java.hw14.datasets.DataSet;

import java.util.Collection;

public interface EntityExecutor {

    <T extends DataSet> void init(Collection<T> dataSets);

    <T extends DataSet> long save(T dataSet);

    <T extends DataSet> T load(long id, Class<T> clazz);
}
