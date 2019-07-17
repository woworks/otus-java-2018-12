package ru.otus.java.hw16.service;

import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.datasets.UserDataSet;

import java.util.List;

public class RemoteDBService implements DBService {
    @Override
    public void save(UserDataSet dataSet) {

    }

    @Override
    public UserDataSet read(long id) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        return null;
    }

    @Override
    public List<UserDataSet> findByName(String name) {
        return null;
    }

    @Override
    public void flush() {

    }
}
