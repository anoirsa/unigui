package data.staticmethods;

public class Outsiders {
    public static String splitNextOrder(String order) {
        String[] splitter = order.split("-");
        return splitter[0];
    }
}
