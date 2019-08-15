package ru.otus.java.hw16.server.server;

import ru.otus.java.hw16.server.annotation.Blocks;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.MessageWorker;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatcherSocketMessageServer implements DispatcherSocketMessageServerMBean {
    private static final int THREADS_COUNT = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService excecutorService;
    private final List<SocketMessageWorker> workers;
    private final Map<Integer, Address.Type> clients;

    public DispatcherSocketMessageServer(Map<Integer, Address.Type> clients){
        System.out.println("DispatcherSocketMessageServer:: Start server");
        excecutorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
        this.clients = clients;
    }

    @Blocks
    public void start() throws Exception{
        excecutorService.submit(this::forward);

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("ServerSocket is created, port: " + PORT);

            while(!excecutorService.isShutdown()){
                System.out.println("ServerSocket will wait for connections on port: " + PORT);
                Socket socket = serverSocket.accept();  //blocks
                System.out.println("ServerSocket accepted connection on port: " + PORT + " from outer socket" + socket);

                System.out.println(" = = == == == == = == = FOUND " + clients.get(socket.getPort()));
                SocketMessageWorker worker = new SocketMessageWorker(socket);
                worker.init(clients.get(socket.getPort()), socket.getPort());
                workers.add(worker);
                System.out.println("DispatcherSocketMessageServer:: worker = " + worker.getAddress());
            }
        }
    }

    private void forward(){
        while (true){
            for (SocketMessageWorker worker : workers){
                Message message = worker.poll();
                if (message != null){
                    System.out.println("[Server] I'm worker with address: " + worker.getAddress());
                    System.out.println("[Server] Worked took a message from: " + message.getTo() + "; to:" + message.getTo());

                    SocketMessageWorker destinationWorker = getDestinationWorker(message.getTo());
                    destinationWorker.send(message);

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

    private SocketMessageWorker getDestinationWorker(Address to) {
        System.out.println("getDestinationWorker:: to = " + to);
        for (SocketMessageWorker worker : workers) {
            System.out.println("getDestinationWorker:: worker address = " + worker.getAddress());

            // we take any DB worker
            if (to.getType().equals(Address.Type.DATABASE)) {
                System.out.println("getDestinationWorker:: found destination DB worker = " + worker);
                return worker;
            }

            // for Frontend - we take exact address
            if (worker.getAddress().equals(to)) {
                System.out.println("getDestinationWorker:: found destination Front worker = " + worker);
                return worker;
            }
        }

        return null;
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
