package lab1.task1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;

        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());

                System.out.println("Server has received message: " + msg);
                System.out.println("Client's address: "
                        + receivePacket.getAddress() + ":"
                        + receivePacket.getPort());

                byte[] sendBuffer = "Server answering to client".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getSocketAddress());
                socket.send(sendPacket);
                System.out.println("Server has answered");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
