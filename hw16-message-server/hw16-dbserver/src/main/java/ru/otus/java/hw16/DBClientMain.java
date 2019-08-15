package ru.otus.java.hw16;

import ru.otus.java.hw16.hibernate.DBInitializerService;
import ru.otus.java.hw16.hibernate.HibernateDBServiceImpl;
import ru.otus.java.hw16.server.base.DBService;
import ru.otus.java.hw16.server.client.ClientSocketMessageWorker;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messages.PingMessage;
import ru.otus.java.hw16.server.messages.SearchService;
import ru.otus.java.hw16.server.messages.SearchServiceImpl;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBClientMain
{
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGE_COUNT = 10;
    public static final int LOCALPORT = 20008;
    private final DBService dbService;
    private final SearchService searchService;

    public static void main( String[] args ) throws InterruptedException, IOException {
        new DBClientMain().start();
    }


    public DBClientMain() {
        this.dbService = new HibernateDBServiceImpl();
        this.searchService = new SearchServiceImpl(null, dbService);
    }

    private void start() throws InterruptedException, IOException {
        new DBInitializerService(dbService).init();

        final SocketMessageWorker client = new ClientSocketMessageWorker(HOST, PORT, LOCALPORT);
        client.init(Address.Type.DATABASE, LOCALPORT);
        System.out.println("Start client ");

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            while (true){
                // wait till new element appears
                //Object msg = client.take();
                Message msg = (Message) client.take();
                System.out.println("[DB] Message received: " + msg.toString());
                msg.exec(searchService, client);
            }
        });

/*        int count = 0;
        while (count < MAX_MESSAGE_COUNT){
            Message message = new PingMessage(client.getAddress());
            client.send(message);
            System.out.println("[DB] Message send: " + message.toString());
            Thread.sleep(PAUSE_MS);
            count++;
        }*/

        Thread.sleep(60_000);

        client.close();
        executorService.shutdown();
    }
}
