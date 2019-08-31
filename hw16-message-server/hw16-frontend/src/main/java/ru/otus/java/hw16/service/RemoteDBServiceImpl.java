package ru.otus.java.hw16.service;

import org.apache.commons.lang3.NotImplementedException;
import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.datasets.UserDataSet;

import java.util.Collections;
import java.util.List;

public class RemoteDBServiceImpl implements DBService {

    private final InOutMessagesService inOutMessagesService;

    public RemoteDBServiceImpl(InOutMessagesService inOutMessagesService) {
        this.inOutMessagesService = inOutMessagesService;
    }

    @Override
    public void save(UserDataSet dataSet) {
        throw new NotImplementedException("Save is not implemented");
    }

    @Override
    public UserDataSet read(long id) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        return inOutMessagesService.sendReadAllMessage();
    }

    @Override
    public List<UserDataSet> findByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public void flush() {
        throw new NotImplementedException("Flush is not implemented");
    }
}
