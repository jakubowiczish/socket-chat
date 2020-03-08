package lab1.homework;

public class Utils {

    public static String formatMessage(String message) {
        return message.replace("\u0000", "").trim();
    }
}
