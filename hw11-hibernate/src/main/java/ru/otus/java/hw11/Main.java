package ru.otus.java.hw11;


import ru.otus.java.hw11.base.DBService;
import ru.otus.java.hw11.datasets.AddressDataSet;
import ru.otus.java.hw11.datasets.PhoneDataSet;
import ru.otus.java.hw11.datasets.UserDataSet;
import ru.otus.java.hw11.hibernate.HibernateDBServiceImpl;
import ru.otus.java.hw11.orm.OrmDBServiceImpl;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        //DBService dbService = new HibernateDBServiceImpl();
        DBService dbService = new OrmDBServiceImpl();

        dbService.save(new UserDataSet("tully", 21, Collections.singletonList(new PhoneDataSet("12345")), new AddressDataSet("Street One")));
        dbService.save(new UserDataSet("sully", 22, Collections.singletonList(new PhoneDataSet("67890")),new AddressDataSet("Street Two")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

    }
}
