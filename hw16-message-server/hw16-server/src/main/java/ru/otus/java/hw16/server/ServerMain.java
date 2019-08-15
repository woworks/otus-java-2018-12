package ru.otus.java.hw16.server;

import ru.otus.java.hw16.server.messagesystem.Address;
import ru.otus.java.hw16.server.runner.ProcessRunnerImpl;
import ru.otus.java.hw16.server.server.DispatcherSocketMessageServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class ServerMain
{
    private static final String CLIENT_START_COMMAND = "java -jar ../_1611-client/target/client.jar -port 5051";
    private static final int CLIENT_START_DELAY_SEC = 5;
    private static final Map<Integer, Address.Type> clients = new HashMap<>();

    public static void main( String[] args ) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        //startClient( executorService );
        //clients.put(, Address.Type.SERVER);
        clients.put(20009, Address.Type.FRONTEND);
        clients.put(20008, Address.Type.DATABASE);

        //MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        //ObjectName objectName = new ObjectName("ru.otus.java:type=Server");

        DispatcherSocketMessageServer server = new DispatcherSocketMessageServer(clients);
        //mBeanServer.registerMBean(server, objectName);

        server.start();

        executorService.shutdown();

    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
               // System.out.println("CLient started");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

    }
}
