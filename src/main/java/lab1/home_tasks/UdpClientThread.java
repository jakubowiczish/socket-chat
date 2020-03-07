package lab1.home_tasks;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class UdpClientThread implements Runnable, Sender {

    public static final int BUFFER_SIZE = 1337;

    private DatagramSocket udpSocket;

    private Set<InetSocketAddress> clientsAddresses;
    private SocketAddress receivedPacketAddress;

    public UdpClientThread(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @SneakyThrows
    @Override
    public void run() {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        clientsAddresses = new LinkedHashSet<>();

        while (true) {
            Arrays.fill(receiveBuffer, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            udpSocket.receive(receivePacket);

            String message = new String(receivePacket.getData());
            clientsAddresses.add((InetSocketAddress) receivePacket.getSocketAddress());
            receivedPacketAddress = receivePacket.getSocketAddress();
            System.out.println(clientsAddresses.size());
            send(message);
        }
    }

    @Override
    public void send(String message) {
        clientsAddresses.stream()
                .filter(this::isNotMyself)
                .forEach(sendMessage(message));
    }

    private Consumer<InetSocketAddress> sendMessage(String message) {
//        String finalMessage = client.getClientIdTag() + message;
        String finalMessage = message;
        return socketAddress -> {
            try {
//                System.out.println("DEBUGGING  " + message + client.getClientId() + socketAddress.getAddress() + socketAddress.getPort());
                DatagramPacket datagramPacket = new DatagramPacket(finalMessage.getBytes(), finalMessage.getBytes().length, socketAddress);
                udpSocket.send(datagramPacket);
            } catch (IOException e) {
                System.out.println("Problem with sending packet using UDP socket");
            }
        };
    }

    private boolean isNotMyself(SocketAddress address) {
        return !Objects.equals(address, receivedPacketAddress);
    }
}
