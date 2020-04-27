package socket_chat.homework;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import static socket_chat.homework.MessageTypeTag.UDP;
import static socket_chat.homework.UdpClientThread.BUFFER_SIZE;

@AllArgsConstructor
public class UdpClientMessageReader implements Runnable {

    private DatagramSocket udpSocket;

    @SneakyThrows
    @Override
    public void run() {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];

        while (true) {
            Arrays.fill(receiveBuffer, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            udpSocket.receive(receivePacket);
            String message = new String(receivePacket.getData());
            System.out.println(UDP.getName() + message);
        }
    }
}
