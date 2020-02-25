package lab1.task3;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class JavaUdpServer3 {

    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;

        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                int msg = ByteBuffer.wrap(receiveBuffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                System.out.println("Server has received message: " + msg);
                System.out.println("Client's address: "
                        + receivePacket.getAddress() + ":"
                        + receivePacket.getPort());

                int sendMsg = msg + 1;
                byte[] sendBuffer = BigInteger.valueOf(sendMsg).toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getSocketAddress());
                socket.send(sendPacket);
                System.out.println("Server has answered with: " + sendMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
