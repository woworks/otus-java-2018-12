package ru.otus.java.hw15.orm;


import ru.otus.java.hw15.base.DBService;
import ru.otus.java.hw15.connection.ConnectionHelper;
import ru.otus.java.hw15.datasets.AddressDataSet;
import ru.otus.java.hw15.datasets.UserDataSet;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class OrmDBServiceImpl implements DBService {

    private EntityExecutor entityExecutor;

    public OrmDBServiceImpl() {
        try {
            this.entityExecutor = new OrmEntityExecutor(ConnectionHelper.getPostgresqlConnection());
            this.entityExecutor.init(Collections.singletonList(new UserDataSet("", 0, Collections.emptyList(), new AddressDataSet())));
        } catch (SQLException e) {
            System.out.println("Could not initialize OrmDBServiceImpl!");
            throw new ExecutorException(e.getMessage());
        }
    }


    @Override
    public void save(UserDataSet dataSet) {
        entityExecutor.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        return entityExecutor.load(id, UserDataSet.class);
    }

    @Override
    public List<UserDataSet> readAll() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserDataSet> findByName(String name) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void flush() {

    }
}
