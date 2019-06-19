package ru.otus.java.hw14;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.java.hw14.base.DBService;
import ru.otus.java.hw14.freemarker.TemplateProcessor;
import ru.otus.java.hw14.handlers.SocketHandler;
import ru.otus.java.hw14.hibernate.DBInitializerService;
import ru.otus.java.hw14.hibernate.HibernateDBServiceImpl;
import ru.otus.java.hw14.servlet.UserServlet;

class WebServerWrapper {
    private final int PORT = 8080;
    private final DBService dbService;
    private final TemplateProcessor templateProcessor;
    private Server server = new Server(PORT);


    WebServerWrapper() {
        dbService = new HibernateDBServiceImpl();
        templateProcessor = new TemplateProcessor();
        new DBInitializerService(dbService).init();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UserServlet(dbService, templateProcessor)), "/users");

        HandlerList handlers = new HandlerList();
        // first element  is webSocket handler, second element is first handler
        handlers.setHandlers(new Handler[] {new SocketHandler(), context});
        server.setHandler(handlers);



    }
    void start() throws Exception {
        server.start();
        server.join();
    }

}
