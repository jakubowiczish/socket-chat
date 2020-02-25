package lab1.task4;

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
                System.out.println(detectLanguage(receivedMessage));
                System.out.println("Server has received message: " + receivedMessage);
                System.out.println("Client's address: " + receivePacket.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String detectLanguage(String message) {
        if (message == null || message.length() < 3)
            return "Could not detect language. Incorrect message received";
        else if (message.charAt(1) == 'J')
            return "Language recognized: JAVA";
        else if (message.charAt(1) == 'P')
            return "Language recognized: PYTHON";
        return "Language not recognized";
    }
}
