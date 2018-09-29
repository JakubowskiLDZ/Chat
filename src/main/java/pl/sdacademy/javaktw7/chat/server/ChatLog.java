package pl.sdacademy.javaktw7.chat.server;

import pl.sdacademy.javaktw7.chat.model.ChatMessage;
import pl.sdacademy.javaktw7.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

public class ChatLog {
    private Map<Socket, ObjectOutputStream> clientConnections;
    private SimpleDateFormat dateFormatter;

    public ChatLog(){
        clientConnections = new LinkedHashMap<>();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }
    private void updateObservers(DatedChatMessage datedChatMessage){
        for (ObjectOutputStream objectOutputStream : clientConnections.values()) {
            try {
                objectOutputStream.writeObject(datedChatMessage);
                objectOutputStream.flush();
            } catch (IOException e) {
                System.out.println(("!!! could not sent message to client: " + e.getMessage()));
            }
        }

    }

    public boolean register(Socket newClient){
        try {
            ObjectOutputStream newClientpStream =
                    new ObjectOutputStream(newClient.getOutputStream());
            clientConnections.put(newClient, newClientpStream);
        } catch (IOException e) {
            System.out.println("!! Someone triied to connect, but was rejected: " +
                    e.getMessage());
            return false;
        }
        return true;

    }
    public boolean unregister(Socket existingClient){
        if(clientConnections.containsKey(existingClient)){
            ObjectOutputStream connectionToBeClosed = clientConnections.remove(existingClient);
            try {
                connectionToBeClosed.close();
            } catch (IOException e) {
            }
            return true;
        }
        return false;
    }
    public void acceptMessage(ChatMessage message){

        DatedChatMessage datedChatMessage = new DatedChatMessage(message);
        printMessage(datedChatMessage);
        updateObservers(datedChatMessage);

    }
    private void printMessage(DatedChatMessage datedChatMessage){

        String formattedDate = dateFormatter.format(datedChatMessage.getReceiveDate());
        String messageToDisply = formattedDate
                + " " + datedChatMessage.getAuthor()
                + ": " + datedChatMessage.getMessage();
        System.out.println(messageToDisply);
    }




}
