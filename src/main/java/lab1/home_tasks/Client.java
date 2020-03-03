package lab1.home_tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.Socket;

@Getter
@AllArgsConstructor
public class Client {

    private int clientId;
    private Socket socket;

    public String getClientIdTag() {
        return "[" + clientId + "]: ";
    }
}
