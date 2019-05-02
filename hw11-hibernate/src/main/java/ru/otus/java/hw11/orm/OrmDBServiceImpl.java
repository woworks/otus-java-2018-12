package ru.otus.java.hw11.orm;


import ru.otus.java.hw11.base.DBService;
import ru.otus.java.hw11.connection.ConnectionHelper;
import ru.otus.java.hw11.datasets.AddressDataSet;
import ru.otus.java.hw11.datasets.UserDataSet;

import java.sql.SQLException;
import java.util.Collections;

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

}
