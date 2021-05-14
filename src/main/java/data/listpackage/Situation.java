package data.listpackage;

import data.enums.Order;

public class Situation {
    private String playerName;
    private Order order;
    private String cardPfield;
    private String cardInputed;
    private String outcome;


    public Situation(String playerName, Order order, String cardPfield, String cardInputed) {
        this.playerName = playerName;
        this.order = order;
        this.cardPfield = cardPfield;
        this.cardInputed = cardInputed;

    }

    public Situation() {
        this.playerName ="";
        this.order = null;
        this.cardPfield = "";
        this.cardInputed = "";
        this.outcome = "";
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCardPfield() {
        return cardPfield;
    }

    public void setCardPfield(String cardPfield) {
        this.cardPfield = cardPfield;
    }

    public String getCardInputed() {
        return cardInputed;
    }

    public void setCardInputed(String cardInputed) {
        this.cardInputed = cardInputed;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "Situation{" +
                "playerName='" + playerName + '\'' +
                ", order=" + order +
                ", cardPfield='" + cardPfield + '\'' +
                ", cardInputed='" + cardInputed + '\'' +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}
