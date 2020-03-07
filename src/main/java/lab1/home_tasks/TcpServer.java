package lab1.home_tasks;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {

    public static final int PORT_NUMBER = 12345;
    private static final int NUMBER_OF_THREADS = 16;

    public static void main(String[] args) throws IOException {
        System.out.println("Server has started");

        List<Client> clients = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS * 2);

        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
             DatagramSocket udpSocket = new DatagramSocket(PORT_NUMBER)) {

            executorService.execute(new UdpClientThread(udpSocket));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clients.size(), clientSocket);
                clients.add(client);
                executorService.execute(new TcpClientThread(client, clients));
            }
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}