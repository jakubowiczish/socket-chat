package lab1.homework;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeTag {

    TCP("<TCP>: "),
    UDP("<UDP>: "),
    MULTICAST("<MULTICAST>: "),
    NAME_TAG("N: ");

    private String name;
}
