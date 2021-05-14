package data.staticmethods;

import data.enums.Order;
import data.listpackage.Situation;

public class Outsiders {
    public static String splitNextOrder(String order) {
        String[] splitter = order.split("-");
        return splitter[0];
    }

    // This method is responsible to analyze the next order string and see an outcome
    // Temporary change to return type String
    public static Situation analyzeOutcome(String str) {
        String[] sections = str.split("-");
        Situation situation = new Situation();
        switch (sections.length) {
            case 3 :
                situation.setPlayerName(sections[2]);
                System.out.println(situation.toString());
                break;
            case 2 :
                situation.setOrder(Order.valueOf(sections[1]));
                situation.setCardPfield(sections[0]);
                break;
            case 4:
                situation = new Situation(sections[2],
                        Order.valueOf(sections[1]),sections[0],sections[3]);
                break;
        }

        return situation;
    }


}
