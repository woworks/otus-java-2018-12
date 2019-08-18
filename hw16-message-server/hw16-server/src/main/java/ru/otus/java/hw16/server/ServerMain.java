package ru.otus.java.hw16.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.runner.ProcessRunnerImpl;
import ru.otus.java.hw16.server.server.DispatcherSocketMessageServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain {
    private static final Logger LOG = LogManager.getLogger(ServerMain.class);
    public static final int FRONT_PORT_1 = 5051;
    public static final int DB_PORT_1 = 5061;
    public static final int DB_PORT_2 = 5062;
    private static final String DB_START_COMMAND_1 = "java -jar ../../hw16-dbserver/target/hw16-dbserver-jar-with-dependencies.jar -port " + DB_PORT_1;
    private static final String DB_START_COMMAND_2 = "java -jar ../../hw16-dbserver/target/hw16-dbserver-jar-with-dependencies.jar -port " + DB_PORT_2;
    private static final String FRONT_START_COMMAND = "java -jar ../../hw16-frontend/target/hw16-frontend-jar-with-dependencies.jar -port " + FRONT_PORT_1;
    private static final int CLIENT_START_DELAY_SEC = 5;
    private static final Map<Integer, Address.Type> clients = new HashMap<>();


    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws IOException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        startClient(executorService);
        clients.put(FRONT_PORT_1, Address.Type.FRONTEND);
        clients.put(DB_PORT_1, Address.Type.DATABASE);
        clients.put(DB_PORT_2, Address.Type.DATABASE);


        DispatcherSocketMessageServer server = new DispatcherSocketMessageServer(clients);
        server.start();

        executorService.shutdown();

    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(DB_START_COMMAND_1);
                new ProcessRunnerImpl().start(DB_START_COMMAND_2);
                new ProcessRunnerImpl().start(FRONT_START_COMMAND);
            } catch (IOException e) {
                LOG.error("Could not start all the client services", e);
            }

        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

    }
}
