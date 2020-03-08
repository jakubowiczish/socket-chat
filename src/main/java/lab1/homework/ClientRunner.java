package lab1.homework;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lab1.homework.MessageTypeTag.MULTICAST;
import static lab1.homework.ServerRunner.PORT_NUMBER;
import static lab1.homework.UdpClientThread.BUFFER_SIZE;

public class ClientRunner {

    private static final String HOST_NAME = "localhost";
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int MULTICAST_PORT_NUMBER = 12346;

    public static void main(String[] args) throws IOException {
        System.out.println("Client has started");

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try (Socket tcpSocket = new Socket(HOST_NAME, PORT_NUMBER);
             DatagramSocket udpSocket = new DatagramSocket(tcpSocket.getLocalPort());
             MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT_NUMBER)) {

            InetAddress groupAddress = InetAddress.getByName(MULTICAST_ADDRESS);

            executorService.execute(new ClientMessageSendingThread(tcpSocket,
                    udpSocket,
                    InetAddress.getByName(HOST_NAME),
                    groupAddress,
                    PORT_NUMBER, MULTICAST_PORT_NUMBER));

            executorService.execute(new UdpClientMessageReader(udpSocket));
            executorService.execute(new TcpClientMessageReader(tcpSocket));

            byte[] receiveBuffer = new byte[BUFFER_SIZE];
            multicastSocket.joinGroup(groupAddress);
            while (true) {
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(packet);
                System.out.println(MULTICAST.getName() + new String(packet.getData()));
            }

        } catch (Exception e) {
            System.out.println("Client error");
        }
    }

}
