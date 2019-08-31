package ru.otus.java.hw16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.hibernate.DBInitializerService;
import ru.otus.java.hw16.hibernate.HibernateDBServiceImpl;
import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.client.ClientSocketMessageWorker;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messages.SearchService;
import ru.otus.java.hw16.server.messages.SearchServiceImpl;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;

public class DBClientMain {
    private static final Logger LOG = LogManager.getLogger(DBClientMain.class);
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int LOCALPORT_DEFAULT = 5061;
    public static int localport = LOCALPORT_DEFAULT;
    private final DBService dbService;
    private final SearchService searchService;

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length == 2) {
            localport = Integer.valueOf(args[1]);
        }
        new DBClientMain().start();
    }


    public DBClientMain() {
        this.dbService = new HibernateDBServiceImpl();
        this.searchService = new SearchServiceImpl(null, dbService);
    }

    private void start() throws InterruptedException, IOException {
        new DBInitializerService(dbService).init();

        final SocketMessageWorker client = new ClientSocketMessageWorker(HOST, PORT, localport);
        client.init(Address.Type.DATABASE, localport);
        LOG.info("Starting DATABASE client, port: {}", localport);

        while (true) {
            try {
            // wait till new element appears
            Message msg = client.take();
            LOG.info("[DB] Message received: " + msg.toString());
            msg.exec(searchService, client);
            } catch (Exception e) {
                LOG.error("Could not process the message", e);
            }
        }
    }
}
