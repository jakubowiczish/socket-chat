package lab1.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.Socket;

@Getter
@AllArgsConstructor
public class Client {

    private int clientId;
    private Socket socket;
}
