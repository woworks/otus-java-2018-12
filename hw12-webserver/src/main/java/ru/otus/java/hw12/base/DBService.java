package ru.otus.java.hw12.base;


import ru.otus.java.hw12.datasets.UserDataSet;

import java.util.List;

public interface DBService {

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    List<UserDataSet> readAll();

}
