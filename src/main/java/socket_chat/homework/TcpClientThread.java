package socket_chat.homework;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static socket_chat.homework.MessageTypeTag.NAME_TAG;
import static socket_chat.homework.MessageTypeTag.TCP;

@AllArgsConstructor
public class TcpClientThread implements Runnable, Sender {

    private Client client;
    private List<Client> clients;

    @Override
    public void run() {
        try {
            System.out.println("Client " + client.getClientId() + " has connected");

            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
                String message = in.readLine();
                if (message == null) continue;
                if (message.startsWith(NAME_TAG.getName())) {
                    updateClientsName(message);
                    continue;
                }

                System.out.println(TCP.getName() + client.getNameTag() + message);
                send(message);
            }
        } catch (IOException e) {
            System.out.println("Client " + client.getClientId() + " disconnected");
        }
    }

    @Override
    public void send(String message) {
        clients.stream()
                .filter(this::isNotMyself)
                .forEach(sendMessage(message));
    }

    private Consumer<Client> sendMessage(String message) {
        final String finalMessage = message.substring(1).trim();
        return client -> {
            PrintWriter out;
            try {
                out = new PrintWriter(client.getSocket().getOutputStream(), true);
            } catch (IOException ex) {
                System.out.println("Problem with getting output stream from ");
                return;
            }
            out.println(this.client.getNameTag() + finalMessage);
        };
    }

    private boolean isNotMyself(Client client) {
        return !Objects.equals(this.client.getSocket(), client.getSocket());
    }

    private void updateClientsName(String message) {
        client.setName(message.substring(NAME_TAG.getName().length()));
    }
}