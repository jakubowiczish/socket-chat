package lab1.home;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class ClientThread extends Thread {

    private Client client;
    private List<Client> clients;

    @SneakyThrows
    @Override
    public void run() {
        try {
            System.out.println("Client " + client.getClientId() + " has started");
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
                String message = in.readLine();
                if (message == null) continue;

                System.out.println("Received message from " + client.getClientId() + ": " + message);

                for (Client availableClient : clients) {
                    if (Objects.equals(client.getSocket(), availableClient.getSocket())) continue;

                    PrintWriter out = new PrintWriter(availableClient.getSocket().getOutputStream(), true);
                    out.println("Client: " + client.getClientId() + " has sent message: " + message);
                }
            }

        } catch (IOException e) {
            System.out.println("Client " + client.getClientId() + " disconnected");
        }
    }
}