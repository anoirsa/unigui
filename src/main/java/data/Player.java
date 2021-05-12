package data;

import java.util.List;
import java.util.function.Function;

public class Player {

    private  String playerName;
    private int extraCards;
    private Status status;
    private Who who;                 // this variable to determine if the player is computer automatic // or normal user
    private List<String> cardLists;
    private int pickedUpCards;
    private Helper helper;
    private Gender gender;
    private String email;
    private String password;




    public Player(String playerName, Status status, List<String> cardLists, Who who) {
        this.playerName = playerName;
        this.status = status;
        this.cardLists = cardLists;
        helper = new Helper(playerName,who);
        this.who = who;
    }

    public Player(String playerName, Status status, Who who, List<String> cardLists, Gender gender, String email, String password) {
        this.playerName = playerName;
        this.status = status;
        this.who = who;
        this.cardLists = cardLists;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public  Player() {

    }
    public String getPlayerName() {
        return playerName;
    }

    public int getHoldingCards() {
        return cardLists.size();
    }

    public List<String> getCardLists() {
        return cardLists;
    }

    public void setCardLists(List<String> cardLists) {
        this.cardLists = cardLists;
    }

    public int getExtraCards() {
        return extraCards;
    }

    public void setExtraCards(int extraCards) {
        this.extraCards = extraCards;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPickedUpCards() {
        return pickedUpCards;
    }

    public void setPickedUpCards(int pickedUpCards) {
        this.pickedUpCards = pickedUpCards;
    }

    public String toString() {
        return "Player{" +
                "playerName='" + getPlayerName() + '\'' +
                ", extraCards=" + extraCards +
                ", status=" + status +
                ", cardLists=" + cardLists +
                '}';
    }

    public Function<String, String> handlePlayerChoices = (order) -> {
        String[] orderSections = order.split("-");
        String cardField = orderSections[0];
        if (status.equals(Status.TURN)) {
            Order orderP = Order.valueOf(orderSections[1]);      // this variable checks the instruction
            // this variable checks the current card in the field
            String cardInputed = " ";
            try {
                cardInputed = helper.displayChoices(cardLists, cardField, orderP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cardInputed.equals("NIP") == false) {
                if(cardInputed.contains("CH")) {
                    cardLists.remove("Demand");
                }
                else cardLists.remove(cardInputed);
                //IMPROVE
                // resetting the indexes of our list
                int j = 0;
                for (String i : cardLists) {
                    cardLists.set(j, i);
                    j++;
                }
                //_____________________
                return cardField+"-"+orderSections[1]+"-"+getWho()+"-"+cardInputed;
                //IMPROVE
                // return cardField+"-"+orderSections[1]+"-"+getPlayerName()+"-"+cardInputed;
            } else{
                //IMPROVE
                return cardField +"-"+orderSections[1]+"-"+getWho()+"-NIP";
                //IMPROVE
                //return cardField +"-"+orderSections[1]+"-"+getPlayerName()+"-NIP";
            }
        } else {
            return order;
        }
    };

    public Who getWho() {
        return who;
    }

    public void setWho(Who who) {
        this.who = who;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
