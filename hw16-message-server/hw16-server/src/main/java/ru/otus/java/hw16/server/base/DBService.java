package ru.otus.java.hw16.server.base;


import ru.otus.java.hw16.server.datasets.UserDataSet;

import java.util.List;

public interface DBService {

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    List<UserDataSet> readAll();

    List<UserDataSet> findByName(String name);

    void flush();
}
