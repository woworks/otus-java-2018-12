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
import ru.otus.java.hw12.freemarker.TemplateProcessor;
import ru.otus.java.hw12.hibernate.DBInitializerService;
import ru.otus.java.hw12.hibernate.HibernateConfiguration;
import ru.otus.java.hw12.hibernate.HibernateDBServiceImpl;
import ru.otus.java.hw12.servlet.UserServlet;

class WebServerWrapper {
    private static final int PORT = 8080;
    private static final DBService dbService;
    private static final TemplateProcessor templateProcessor;


    static {
        Configuration configuration = new HibernateConfiguration()
                .configFile("hw12-webserver/src/main/resources/hibernate.cfg.xml")
                .classes(UserDataSet.class, AddressDataSet.class, PhoneDataSet.class, EmptyDataSet.class)
                .build();

        dbService = new HibernateDBServiceImpl(configuration);
        templateProcessor = new TemplateProcessor();
        new DBInitializerService(dbService).init();

    }
    void start() throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new UserServlet(dbService, templateProcessor)), "/users");
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(context));

        server.start();
        server.join();
    }

}
