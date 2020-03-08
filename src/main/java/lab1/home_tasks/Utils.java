package lab1.home_tasks;

public class Utils {

    public static String formatMessage(String message) {
        return message.replace("\u0000", "").trim();
    }
}
