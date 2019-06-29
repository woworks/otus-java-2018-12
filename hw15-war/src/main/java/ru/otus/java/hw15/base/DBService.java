package ru.otus.java.hw15.base;


import ru.otus.java.hw15.datasets.UserDataSet;

import java.util.List;

public interface DBService {

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    List<UserDataSet> readAll();

    List<UserDataSet> findByName(String name);

    void flush();
}
