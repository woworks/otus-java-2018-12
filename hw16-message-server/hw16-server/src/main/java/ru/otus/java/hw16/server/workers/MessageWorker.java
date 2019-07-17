package ru.otus.java.hw16.server.workers;

import ru.otus.java.hw16.server.annotation.Blocks;
import ru.otus.java.hw16.server.messages.Message;

import java.io.IOException;

public interface MessageWorker {
    Message poll();

    void send(Message message);

    @Blocks
    Message take() throws InterruptedException;

    void close() throws IOException;
}
