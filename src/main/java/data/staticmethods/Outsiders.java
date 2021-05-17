package data.staticmethods;

import data.Player;
import data.enums.Order;
import data.enums.Status;
import data.listpackage.Situation;
import javafx.scene.control.Alert;

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

    public static Alert alertOfWin(Player player1, Player player2) {
        Alert alertWinner = new Alert(Alert.AlertType.CONFIRMATION);

        // This variable is to determine the winner of the game
        String winnerName = player1.getStatus().equals(Status.WIN) ? player1.getPlayerName() :
                player2.getPlayerName();

        alertWinner.setContentText("The game has ended and the winner of the game is "+ winnerName);
        return alertWinner;
    }


}
