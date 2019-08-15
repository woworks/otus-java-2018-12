package ru.otus.java.hw16.server.client;

import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientSocketMessageWorker extends SocketMessageWorker {

    private final Socket socket;
    public ClientSocketMessageWorker(String host, int port, int localport) throws IOException {
        this(new Socket(host, port, InetAddress.getByName("localhost"), localport));
    }

    public ClientSocketMessageWorker(Socket socket) {
        super(socket);
        this.socket = socket;
        try {
            this.socket.setReuseAddress(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        socket.close();
    }

}
