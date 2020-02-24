import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class JavaUdpClient {

    public static void main(String[] args) throws Exception {
        System.out.println("JAVA UDP CLIENT");

        int portNumber = 9008;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");

            byte[] sendBuffer = "Ping Java Udp".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
            socket.send(sendPacket);
            System.out.println("Client has send packet");

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            System.out.println("Client has received message: " + new String(receiveBuffer));
            System.out.println("Server's address: " + receivePacket.getAddress());

            Arrays.fill(receiveBuffer, (byte) 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
