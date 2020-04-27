package socket_chat.homework;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static socket_chat.homework.MessageTypeTag.NAME_TAG;
import static socket_chat.homework.MessageTypeTag.UDP;
import static socket_chat.homework.Utils.formatMessage;

public class UdpClientThread implements Runnable, Sender {

    public static final int BUFFER_SIZE = 1337;

    private DatagramSocket udpSocket;
    private Map<SocketAddress, String> clientsMap;
    private SocketAddress receivedPacketAddress;

    public UdpClientThread(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @SneakyThrows
    @Override
    public void run() {
        clientsMap = new LinkedHashMap<>();
        byte[] receiveBuffer = new byte[BUFFER_SIZE];

        while (true) {
            Arrays.fill(receiveBuffer, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            udpSocket.receive(receivePacket);

            String message = new String(receivePacket.getData());
            SocketAddress address = receivePacket.getSocketAddress();

            if (message.startsWith(NAME_TAG.getName())) {
                updateClientsName(address, message);
                continue;
            }

            receivedPacketAddress = receivePacket.getSocketAddress();
            String name = clientsMap.get(receivedPacketAddress);

            String messageToSend = formatMessage("[" + name + "]: " + message.substring(1));
            System.out.println(UDP.getName() + messageToSend);
            send(messageToSend);
        }
    }

    @Override
    public void send(String message) {
        clientsMap.keySet().stream()
                .filter(this::isNotMyself)
                .forEach(sendMessage(message));
    }

    private Consumer<SocketAddress> sendMessage(String message) {
        return socketAddress -> {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(
                        message.getBytes(),
                        message.getBytes().length,
                        socketAddress);

                udpSocket.send(datagramPacket);
            } catch (IOException e) {
                System.out.println("Problem with sending packet using UDP socket");
            }
        };
    }

    private boolean isNotMyself(SocketAddress address) {
        return !Objects.equals(address, receivedPacketAddress);
    }

    private void updateClientsName(SocketAddress address, String message) {
        clientsMap.put(address,
                message.substring(NAME_TAG.getName().length()));
    }
}
