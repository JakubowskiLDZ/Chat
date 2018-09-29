package pl.sdacademy.javaktw7.chat.client;

import java.io.IOException;

public class ClientTerminalMain {

    public static void main(String[] args) {

    }
    Runnable clientTerminalTask;

    {
        try {
            clientTerminalTask = new ClientTerminal();
            Thread clientThread = new Thread(clientTerminalTask);
            clientThread.start();
            clientThread.join();
        } catch (IOException e) {
            System.out.println("Could not connect to remote server");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Applicaation was killed");
        }
    }
}
