package ru.otus.java.hw11.base;


import ru.otus.java.hw11.datasets.UserDataSet;

public interface DBService {

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

}
