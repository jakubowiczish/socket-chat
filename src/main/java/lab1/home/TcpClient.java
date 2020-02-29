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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Ping Java Tcp");

            BufferedReader clientsMessage = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (in.ready()) {
                    System.out.println("Received response: " + in.readLine());
                }
                if (clientsMessage.ready()) {
                    out.println(clientsMessage.readLine());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
