package lab1.home_tasks;

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

@AllArgsConstructor
public class ClientsMessageThread implements Runnable {

    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private InetAddress address;
    private int port;

    @SneakyThrows
    @Override
    public void run() {
        BufferedReader clientsMessage = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            if (clientsMessage.ready())
                send(clientsMessage.readLine());
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
            default:
                System.out.println("INCORRECT MESSAGE RECEIVED");
        }
    }


    @SneakyThrows
    private void sendTcpMessage(String message) {
        PrintWriter socketOutput = new PrintWriter(tcpSocket.getOutputStream(), true);
        socketOutput.println(message);
    }

    @SneakyThrows
    private void sendUdpMessage(String message) {
        DatagramPacket datagramPacket = new DatagramPacket(
                message.getBytes(),
                message.getBytes().length,
                address,
                port
        );

        udpSocket.send(datagramPacket);
    }
}
