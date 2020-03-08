package lab1.homework;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import static lab1.homework.MessageTypeTag.NAME_TAG;


@AllArgsConstructor
public class ClientMessageSendingThread implements Runnable {

    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private InetAddress udpAddress;
    private InetAddress multicastAddress;
    private int port;
    private int multicastPort;

    @SneakyThrows
    @Override
    public void run() {
        BufferedReader clientsMessageReader = new BufferedReader(new InputStreamReader(System.in));
        chooseNickname(clientsMessageReader);

        while (true) {
            if (clientsMessageReader.ready())
                send(clientsMessageReader.readLine());
        }
    }

    @SneakyThrows
    private void send(String message) {
        if (StringUtils.isBlank(message)) {
            System.out.println("INCORRECT MESSAGE RECEIVED");
            return;
        }

        switch (message.charAt(0)) {
            case 'T': {
                sendTcpMessage(message);
                return;
            }
            case 'U': {
                sendUdpMessage(message);
                return;
            }
            case 'M': {
                sendMulticastMessage(message);
                return;
            }
            default:
                System.out.println("INCORRECT MESSAGE RECEIVED");
        }
    }


    @SneakyThrows
    private void sendTcpMessage(String message) {
        createPrintWriter(tcpSocket).println(message);
    }

    @SneakyThrows
    private void sendUdpMessage(String message) {
        DatagramPacket datagramPacket = createDatagramPacket(message, udpAddress, port);
        udpSocket.send(datagramPacket);
    }

    @SneakyThrows
    private void sendMulticastMessage(String message) {
        DatagramSocket multicastDatagramSocket = new DatagramSocket();
        DatagramPacket multicastPacket = createDatagramPacket(message.substring(1), multicastAddress, multicastPort);
        multicastDatagramSocket.send(multicastPacket);
    }

    private DatagramPacket createDatagramPacket(String message, InetAddress address, int port) {
        return new DatagramPacket(message.getBytes(), message.getBytes().length, address, port);
    }

    @SneakyThrows
    private PrintWriter createPrintWriter(Socket socket) {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    @SneakyThrows
    private void chooseNickname(BufferedReader reader) {
        System.out.print("Choose your nickname: ");
        String name = reader.readLine();
        System.out.println("Your name is: " + name);

        String nameChoiceMessage = NAME_TAG.getName() + name;
        createPrintWriter(tcpSocket).println(nameChoiceMessage);
        udpSocket.send(createDatagramPacket(nameChoiceMessage, udpAddress, port));
    }
}
