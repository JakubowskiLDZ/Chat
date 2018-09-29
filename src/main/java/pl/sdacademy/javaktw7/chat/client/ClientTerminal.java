package pl.sdacademy.javaktw7.chat.client;

import pl.sdacademy.javaktw7.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static jdk.internal.logger.SimpleConsoleLogger.outputStream;

public class ClientTerminal implements Runnable {

    private final Socket socket;

    public ClientTerminal()throws IOException {
        socket = new Socket("adres na który będziemy się łączyć", 5567);
    }

    @Override
    public void run() {
        ObjectOutputStream outputStream;

        try (ObjectOutputStream connection = new ObjectOutputStream(socket.getOutputStream())){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Your user name: ");
            String username = scanner.nextLine();
            String message = "";
            while (!message.equalsIgnoreCase("exit")){
                System.out.println("> "); //znak zachęty
                message = scanner.nextLine() ;

                ChatMessage messageToSent = new ChatMessage(username, message);
                connection.writeObject(messageToSent);
            }

        } catch (IOException e) {
            System.out.println("Could not prepare object connection, " + "propably server went down.");
            System.out.println(e.getMessage());
        }
        System.out.println("Disconnected from chat");


    }
}
