package ru.otus.java.hw16.hibernate;


import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.datasets.AddressDataSet;
import ru.otus.java.hw16.server.datasets.PhoneDataSet;
import ru.otus.java.hw16.server.datasets.UserDataSet;

import java.util.Arrays;
import java.util.Collections;

public class DBInitializerService {
    private DBService dbService;

    public DBInitializerService(DBService dbService) {
        this.dbService = dbService;
    }

    public void init() {
        if (dbService.readAll().size() == 0) {
            AddressDataSet address1 = new AddressDataSet("Street One");
            AddressDataSet address2 = new AddressDataSet("Street Two");
            dbService.save(new UserDataSet("tully", 19, Arrays.asList(new PhoneDataSet("+123456"), new PhoneDataSet("+654321")), address1));
            dbService.save(new UserDataSet("sully", 20, Collections.singletonList(new PhoneDataSet("67890")), address2));
            dbService.save(new UserDataSet("vally", 21, Collections.singletonList(new PhoneDataSet("12122")), address2));
            dbService.save(new UserDataSet("hally", 22, Arrays.asList(new PhoneDataSet("+223456"), new PhoneDataSet("+114321")), address1));
            dbService.save(new UserDataSet("molly", 23, Collections.singletonList(new PhoneDataSet("34343")), address2));
            dbService.flush();
        }
    }
}
