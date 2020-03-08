package lab1.laboratory.task4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class JavaUdpServer4 {

    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;

        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String receivedMessage = new String(receiveBuffer);
                String detectedLanguage = detectLanguage(receivedMessage);
                System.out.println("LANGUAGE: " + detectedLanguage);
                System.out.println("Server has received message: " + receivedMessage);
                System.out.println("Client's address: "
                        + receivePacket.getAddress() + ":"
                        + receivePacket.getPort());

                byte[] sendBuffer = ("PING " + detectedLanguage).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getSocketAddress());
                socket.send(sendPacket);
                System.out.println("Server has answered with: " + new String(sendBuffer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String detectLanguage(String message) {
        if (message == null || message.length() < 3)
            return "UNKNOWN";
        else if (message.charAt(1) == 'J')
            return "JAVA";
        else if (message.charAt(1) == 'P')
            return "PYTHON";
        return "UNKNOWN";
    }
}
