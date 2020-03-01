package lab1.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient {

    public static void main(String[] args) throws IOException {
        System.out.println("JAVA TCP CLIENT");
        String hostName = "localhost";
        int portNumber = 12345;

        try (Socket socket = new Socket(hostName, portNumber)) {
            PrintWriter socketOutput = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader clientsMessage = new BufferedReader(new InputStreamReader(System.in));

            socketOutput.println("Ping Java Tcp");
            while (true) {
                if (socketInput.ready()) {
                    System.out.println("Received response: " + socketInput.readLine());
                }
                if (clientsMessage.ready()) {
                    socketOutput.println(clientsMessage.readLine());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
