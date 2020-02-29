package lab1.home;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

@AllArgsConstructor
public class ClientThread extends Thread {

    private int clientId;
    private Socket socket;
    private List<Socket> clients;

    @SneakyThrows
    @Override
    public void run() {
        try {
            System.out.println("Client: " + clientId + " has started");
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                if (message != null) {
                    System.out.println("Received message from: " + clientId);
                    for (Socket client : clients) {
                        if (client != socket) {
                            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                            out.println("Client: " + clientId + " has sent message: " + message);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
