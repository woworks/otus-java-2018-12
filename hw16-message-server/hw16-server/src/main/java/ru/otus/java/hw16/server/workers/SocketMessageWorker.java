package ru.otus.java.hw16.server.workers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.java.hw16.server.annotation.Blocks;
import ru.otus.java.hw16.server.messages.Message;
import ru.otus.java.hw16.server.messagesystem.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketMessageWorker implements MessageWorker {
    private static final int WORKER_COUNT = 2;

    private final ExecutorService executorService;
    private final Socket socket;
    private Address address;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    public SocketMessageWorker(Socket socket) {
        this.socket = socket;
        executorService = Executors.newFixedThreadPool(WORKER_COUNT);
    }

    public void init(Address.Type type, Integer localport) {
        this.address = new Address(type, localport);
        executorService.execute(this::sendMessage);
        executorService.execute(this::receiveMessage);
    }

    //Retrieves and removes the head of this queue, or returns null if this queue is empty.
    @Override
    public Message poll() {
        return input.poll();
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    //Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }

    @Blocks
    private void sendMessage(){
        System.out.println("sendMessage::.... this = " + this.getClass() + " socket: " + socket);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
            while (socket.isConnected()){
                System.out.println("sendMessage:: while..");
                Message message = output.take();
                String json = new Gson().toJson(message);
                out.println(json);
                out.println();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Blocks
    private void receiveMessage(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()){
                    String json = stringBuilder.toString();
                    Message message = getMessageFromGson(json);
                    input.add(message);
                    stringBuilder = new StringBuilder();
                }
            }
        }  catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Message getMessageFromGson(String json) throws ClassNotFoundException {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(json);
        //String className = String.valueOf(jsonObject.get(Message.CLASS_NAME_VARIABLE));

        //Class<?> messageClass =  PingMessage.class;

        Class<?> messageClass =  Class.forName(jsonObject.get("type").getAsString());

        return (Message) new Gson().fromJson(json, messageClass);
    }

    public Address getAddress() {
        return address;
    }

}
