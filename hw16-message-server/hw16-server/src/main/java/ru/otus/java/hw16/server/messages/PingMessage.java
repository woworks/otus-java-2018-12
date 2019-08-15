package ru.otus.java.hw16.server.messages;

import ru.otus.java.hw16.server.base.ServerService;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;


public class PingMessage extends MsgToServer {
    private final long time;
    private final Address backAddress;

    public PingMessage(Address from) {
        super(from, from);
        backAddress = from;
        time = System.currentTimeMillis();
    }

    @Override
    public void exec(ServerService serverService, SocketMessageWorker worker) {
        serverService.ping(backAddress);
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PingMessage{" +
                "time=" + time +
                ", backAddress=" + backAddress +
                '}';
    }
}
