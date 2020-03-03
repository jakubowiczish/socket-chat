package lab1.home_tasks;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static lab1.home_tasks.MessageTypeTag.TCP;

@AllArgsConstructor
public class TcpClientMessageReader implements Runnable {

    private Socket tcpSocket;

    @SneakyThrows
    @Override
    public void run() {
        BufferedReader socketInput = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

        while (true) {
            if (socketInput.ready()) {
                System.out.println(TCP.getName() + socketInput.readLine());
            }
        }
    }
}
