package ru.otus.java.hw16.server.server;

import ru.otus.java.hw16.server.annotation.Blocks;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.workers.MessageWorker;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoSocketMessageServer implements EchoSocketMessageServerMBean {
    private static final int THREADS_COUNT = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService excecutorService;
    private final List<MessageWorker> workers;

    public EchoSocketMessageServer(){
        System.out.println("Start server");
        excecutorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
    }

    @Blocks
    public void start() throws Exception{
        excecutorService.submit(this::mirror);

        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            while(!excecutorService.isShutdown()){
                Socket socket = serverSocket.accept();  //blocks
                SocketMessageWorker worker = new SocketMessageWorker(socket);
                worker.init();
                workers.add(worker);
            }
        }
    }

    private void mirror(){
        while (true){
            for (MessageWorker worker : workers){
                Message message = worker.poll();
                if (message != null){
                    System.out.println("Mirroring the message: " + message.toString());
                    worker.send(message);
                    message = worker.poll();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean getRunning(){
        return true;
    }

    @Override
    public void setRunning(boolean running){
        if (!running){
            excecutorService.shutdown();
        }
    }
}
