package app;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started on port 12345");

        Map<String, Socket> clients = new HashMap<>();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            String clientName = "client-" + clients.size();
            clients.put(clientName, clientSocket);

            System.out.println("[SERVER] " + clientName + " successfully connected");

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    while (true){
                        String command = in.readLine();
                        if (command.equals("exit")) {
                            clients.remove(clientName);
                            System.out.println("[SERVER] " + clientName + " disconnected");
                            break;
                        }
                        // Обробка інших команд
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
