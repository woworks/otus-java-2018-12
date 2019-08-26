package ru.otus.java.hw16.server.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.server.annotation.Blocks;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatcherSocketMessageServer implements DispatcherSocketMessageServerMBean {
    private static final Logger LOG = LogManager.getLogger(DispatcherSocketMessageServer.class);
    private static final int THREADS_COUNT = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService excecutorService;
    private final List<SocketMessageWorker> workers;
    private final Map<Integer, Address.Type> clients;

    public DispatcherSocketMessageServer(Map<Integer, Address.Type> clients) {
        LOG.info("DispatcherSocketMessageServer:: Start server..");
        excecutorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
        this.clients = clients;
    }

    @Blocks
    public void start() throws IOException {
        excecutorService.submit(this::forward);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOG.info("ServerSocket is created, port: {}", PORT);

            while (!excecutorService.isShutdown()) {
                Socket socket = serverSocket.accept();  //blocks
                LOG.info("{} accepted connection from outer socket: {}", serverSocket, socket);
                LOG.info("= New Client Connected: {}", clients.get(socket.getPort()));
                SocketMessageWorker worker = new SocketMessageWorker(socket);
                worker.init(clients.get(socket.getPort()), socket.getPort());
                workers.add(worker);
                LOG.info("DispatcherSocketMessageServer:: worker = {}", worker.getAddress());
            }
        }
    }

    private void forward() {
        while (true) {

            try {
                for (SocketMessageWorker worker : workers) {
                    Message message = worker.poll();
                    if (message != null) {
                        LOG.info("[Server] I'm worker with address: {}", worker.getAddress());
                        LOG.info("[Server] Worked took a message from: {}; to: {}", message.getTo(), message.getTo());
                        SocketMessageWorker destinationWorker = getDestinationWorker(message.getTo());
                        destinationWorker.send(message);

                        message = worker.poll();
                    }
                }

                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                LOG.error("Sleep failed", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                LOG.error("Could not forward message", e);
            }
        }

    }

    private SocketMessageWorker getDestinationWorker(Address to) {
        LOG.info("getDestinationWorker:: to = {}", to);
        LOG.info("getDestinationWorker:: workers = {}", workers);
        for (SocketMessageWorker worker : workers) {
            if (worker.getSocket().isClosed()) {
                LOG.warn("Worker is closed: {}", worker.getSocket());
                workers.remove(worker);
                continue;
            }
            // we take any DB worker
            if (to.getType().equals(Address.Type.DATABASE) && worker.getAddress().getType().equals(Address.Type.DATABASE)) {
                LOG.info("getDestinationWorker:: found destination DB worker = {}", worker.getSocket());
                return worker;
            }

            // for Frontend - we take exact address
            if (worker.getAddress().equals(to)) {
                LOG.info("getDestinationWorker:: found destination Front worker = {}", worker.getSocket());
                return worker;
            }
        }

        return null;
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            excecutorService.shutdown();
        }
    }
}
