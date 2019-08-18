package ru.otus.java.hw16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.server.client.ClientSocketMessageWorker;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;

public class FrontClientMain {
    private static final Logger LOG = LogManager.getLogger(FrontClientMain.class);
    private static final int LOCALPORT_DEFAULT = 5051;
    public static int localport = LOCALPORT_DEFAULT;
    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
             localport = Integer.valueOf(args[1]);
        }
        new FrontClientMain().start();
    }

    private void start() throws IOException {
        final SocketMessageWorker client = new ClientSocketMessageWorker(HOST, PORT, localport);
        client.init(Address.Type.FRONTEND, localport);

        LOG.info("FrontClientMain:: starting web... ");

        try {
            new WebServerWrapper(client).start();
        } catch (Exception e) {
           LOG.error("Cannot start WebServerWrapper", e);
        }
    }
}
