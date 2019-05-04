package ru.otus.java.hw12;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.cfg.Configuration;
import ru.otus.java.hw12.base.DBService;
import ru.otus.java.hw12.datasets.AddressDataSet;
import ru.otus.java.hw12.datasets.EmptyDataSet;
import ru.otus.java.hw12.datasets.PhoneDataSet;
import ru.otus.java.hw12.datasets.UserDataSet;
import ru.otus.java.hw12.hibernate.HibernateConfiguration;
import ru.otus.java.hw12.hibernate.HibernateDBServiceImpl;
import ru.otus.java.hw12.servlet.UserServlet;

import java.util.Arrays;
import java.util.Collections;

public class Main {
    private static final int PORT = 8080;
    private static final DBService dbService;

    static {
        Configuration configuration = new HibernateConfiguration()
                .configFile("hw12-webserver/src/main/resources/hibernate.cfg.xml")
                .classes(UserDataSet.class, AddressDataSet.class, PhoneDataSet.class, EmptyDataSet.class)
                .build();

        dbService = new HibernateDBServiceImpl(configuration);

    }

    public static void main(String[] args) throws Exception {
        initDB();
        new Main().start();
    }

    private static void initDB() {
        AddressDataSet address1 = new AddressDataSet("Street One");
        AddressDataSet address2 = new AddressDataSet("Street Two");
        dbService.save(new UserDataSet("tully", 19, Arrays.asList(new PhoneDataSet("+123456"),new PhoneDataSet("+654321")), address1));
        dbService.save(new UserDataSet("sully", 20, Collections.singletonList(new PhoneDataSet("67890")), address2));
        dbService.save(new UserDataSet("vally", 21, Collections.singletonList(new PhoneDataSet("12122")), address2));
        dbService.save(new UserDataSet("hally", 22, Arrays.asList(new PhoneDataSet("+223456"),new PhoneDataSet("+114321")), address1));
        dbService.save(new UserDataSet("molly", 23, Collections.singletonList(new PhoneDataSet("34343")), address2));
    }

    private void start() throws Exception {


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new UserServlet(dbService)), "/users");
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(context));

        server.start();
        server.join();
    }
}
