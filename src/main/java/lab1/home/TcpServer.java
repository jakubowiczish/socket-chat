package lab1.home;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {

    public static void main(String[] args) throws IOException {

        System.out.println("JAVA TCP SERVER");
        int portNumber = 12345;

        List<Client> clients = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(new Client(clients.size(), clientSocket));
                executorService.submit(new ClientThread(new Client(clients.size(), clientSocket), clients));
            }
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }

}
