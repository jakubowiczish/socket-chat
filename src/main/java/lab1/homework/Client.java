package lab1.homework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Getter
@AllArgsConstructor
public class Client {

    private int clientId;
    private Socket socket;

    @Setter
    private String name;

    public String getNameTag() {
        return "[" + name + "]: ";
    }
}
