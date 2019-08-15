package ru.otus.java.hw16;

import ru.otus.java.hw16.server.client.ClientSocketMessageWorker;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class FrontClientMain
{
    public static final int LOCALPORT = 20009;
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGE_COUNT = 3;

    public static void main( String[] args ) throws InterruptedException, IOException {
        new FrontClientMain().start();
    }

    private void start() throws InterruptedException, IOException {
        final SocketMessageWorker client = new ClientSocketMessageWorker(HOST, PORT, LOCALPORT);
        client.init(Address.Type.FRONTEND, LOCALPORT);

        System.out.println("FrontClientMain:: starting web... ");

        try {
            new WebServerWrapper(client).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("FrontClientMain:: Web started ");

        /*ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            while (true){
                //Object msg = client.take();
                MsgGlobalSearchReply msg = (MsgGlobalSearchReply)client.take();
                System.out.println("FrontClientMain:: Front Message received: " + msg.toString());
                msg.exec(null, null);
            }
        });

        int count = 0;
        while (count < MAX_MESSAGE_COUNT){
            //Message message = new PingMessage(client.getAddress());
            Message message = new MsgGlobalSearch(client.getAddress(), new Address(Address.Type.DATABASE, 0), "hally", "SESSION_ID");
            client.send(message);
            System.out.println("FrontClientMain:: Message send: " + message.toString());
            Thread.sleep(PAUSE_MS);
            count++;
        }

        client.close();
        executorService.shutdown();*/
    }
}
