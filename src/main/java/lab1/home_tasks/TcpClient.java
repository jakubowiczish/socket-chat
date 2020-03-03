package lab1.home_tasks;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lab1.home_tasks.TcpServer.PORT_NUMBER;
import static lab1.home_tasks.UdpClientThread.BUFFER_SIZE;

public class TcpClient {

    private static final String HOST_NAME = "localhost";

    public static void main(String[] args) throws IOException {
        System.out.println("JAVA TCP CLIENT");

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try (Socket tcpSocket = new Socket(HOST_NAME, PORT_NUMBER);
             DatagramSocket udpSocket = new DatagramSocket(tcpSocket.getLocalPort())) {
//            BufferedReader socketInput = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

            executorService.execute(new ClientsMessageThread(tcpSocket, udpSocket, InetAddress.getByName(HOST_NAME), PORT_NUMBER));
            executorService.execute(new UdpClientMessageReader(udpSocket));
            executorService.execute(new TcpClientMessageReader(tcpSocket));

            byte[] receiveBuffer = new byte[BUFFER_SIZE];

            while (true) {
//                if (socketInput.ready()) {
//                    System.out.println(TCP.getName() + socketInput.readLine());
//                }
            }

//                if (udpSocket.isConnected()) {
//                    Arrays.fill(receiveBuffer, (byte) 0);
//                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
//                    udpSocket.receive(receivePacket);
//                    String message = new String(receivePacket.getData());
//                    System.out.println(UDP.getName() + message);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
