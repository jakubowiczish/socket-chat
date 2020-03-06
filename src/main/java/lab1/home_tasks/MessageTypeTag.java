package lab1.home_tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeTag {

    TCP("<TCP>: "),
    UDP("<UDP>: "),
    MULTICAST("<MULTICAST>: ");

    private String name;
}
