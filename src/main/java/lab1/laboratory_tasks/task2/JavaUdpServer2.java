package lab1.laboratory_tasks.task2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class JavaUdpServer2 {

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
