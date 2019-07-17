package ru.otus.java.hw16;

import ru.otus.java.hw16.server.client.ClientSocketMessageWorker;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messages.PingMessage;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class FrontClientMain
{
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGE_COUNT = 10;

    public static void main( String[] args ) throws InterruptedException, IOException {
        new FrontClientMain().start();
    }

    private void start() throws InterruptedException, IOException {
        final SocketMessageWorker client = new ClientSocketMessageWorker(HOST, PORT);
        client.init();
        System.out.println("Start client ");

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            while (true){
                Object msg = client.take();
                System.out.println("Front Message received: " + msg.toString());
            }
        });

        int count = 0;
        while (count < MAX_MESSAGE_COUNT){
            Message message = new PingMessage();
            client.send(message);
            System.out.println("Message send: " + message.toString());
            Thread.sleep(PAUSE_MS);
            count++;
        }

        client.close();
        executorService.shutdown();
    }
}
