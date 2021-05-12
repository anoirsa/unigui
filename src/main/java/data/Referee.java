package data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Referee {
    private Player player1;
    private Player player2;
    private List<String> cardList;
    private String order;
    private int cardPickerCounter;



    public Referee(Player player1, Player player2, List<String> cardList, String order) {
        this.player1 = player1;
        this.player2 = player2;
        this.cardList = cardList;
        this.order = order;
        this.cardPickerCounter = 0;
    }
    // method for shuffle the first card
    public Predicate<String> notJSD = (card) -> !(card.equals("Joker") ||
            card.equals("Suspend") ||
            card.equals("Demand") ||
            card.contains("Pay"));

    public Supplier<String> getFirstCard = () ->{
        int getFirstCardIndex = 0;
        String firstCard;
        do {
            firstCard = cardList.get(getFirstCardIndex);
            getFirstCardIndex++;
        } while (!notJSD.test(firstCard));

        getCardList().remove(0);
        String playerFirst = "";
        if (player1.getStatus().equals(Status.TURN)) {
            playerFirst = player1.getPlayerName();
        }
        else {
            playerFirst = player2.getPlayerName();
        }
        return  firstCard+"-PLAY_CARD-"+playerFirst;
    } ;

    public Function<String , String> handleOutcome  = nextOrder -> {
        String[] validateNextOrder = nextOrder.split("-");
        //  Try in
        Map<Who ,Player> mapping = Map.of(Who.USER, player1 , Who.COMPUTER, player2);
        //IMPROVE
        Player assessedPlayer = mapping.get(Who.valueOf(validateNextOrder[2]));
        Player counterPlayer = new Player() ;
        for (Map.Entry<Who,Player> entry : mapping.entrySet()) {
            counterPlayer = entry.getKey() != assessedPlayer.getWho() ? entry.getValue() : counterPlayer;
        }

        // Try in
        String nextOrderCard = validateNextOrder[0];     // default inputed next
        assessedPlayer.setStatus(Status.OFF);
        counterPlayer.setStatus(Status.TURN);

        if (validateNextOrder[3].equals("NIP")) {
            int no = 0;
            switch (getStatus(validateNextOrder[0])) {
                case JOKER:
                case PAY:
                    no = cardPickerCounter;
                    cardPickerCounter = 0;
                    break;
                default:
                    no = 1;
                    break;
            }

            List<String>[] redCards = movingcards(cardList, assessedPlayer.getCardLists(), no);
            assessedPlayer.setCardLists(redCards[1]);
            cardList = redCards[0];
            validateNextOrder[1] = "PLAY_CARD";
        }
        else if (validateNextOrder[3].equals("Suspend")) {
            assessedPlayer.setStatus(Status.TURN);
            counterPlayer.setStatus(Status.OFF);
            nextOrderCard ="Suspend";
            validateNextOrder[1] = "PLAY_CARD";

        } else if (validateNextOrder[3].equals("Joker") || validateNextOrder[3].contains("Pay")) {
            validateNextOrder[1] = "PICK_CARDS";
            if (validateNextOrder[3].equals("Joker")) {
                nextOrderCard = "Joker";
                cardPickerCounter += 4;
            } else if (validateNextOrder[3].contains("Pay")) {
                cardPickerCounter += 2;
                nextOrderCard = validateNextOrder[3];
            }
        }
        else {
            nextOrderCard = validateNextOrder[3];
            validateNextOrder[1] = "PLAY_CARD";
        }
        // enhance
        setPlayer1(assessedPlayer.getPlayerName().equals(player1.getPlayerName()) ? assessedPlayer : counterPlayer);
        setPlayer2(assessedPlayer.getPlayerName().equals(player2.getPlayerName()) ? assessedPlayer : counterPlayer);
        //enhance
        return nextOrderCard +"-"+validateNextOrder[1];
    };

    public Predicate<List<String>> handEndOfTheGame = list ->{
        reshuffleCards(list);
        if (player1.getCardLists().size() == 0 || player2.getCardLists().size() == 0) {
            System.out.println("Game has ended");
            if (player1.getCardLists().size() == 0) player1.setStatus(Status.WIN);
            if (player2.getCardLists().size() == 0) player2.setStatus(Status.WIN);
            return true;
        }
        else return false;
    };

    public Player getPlayer1() {
        return player1;
    }

    public List<String>[] movingcards (List<String> source , List<String> dest , int numberOfCards) {
        List<String> sourceS = source;
        List<String> destT = dest;

        for (int i = 0 ; i < numberOfCards ; i++) {
            String movedCard = sourceS.get(sourceS.size() - 1);
            destT.add(movedCard);
            sourceS.remove(sourceS.size() - 1);
        }
        return new List[]{source, dest};

    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<String> getCardList() {
        return cardList;
    }

    public void setCardList(List<String> cardList) {
        this.cardList = cardList;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getCardPickerCounter() {
        return cardPickerCounter;
    }

    public void setCardPickerCounter(int cardPickerCounter) {
        this.cardPickerCounter = cardPickerCounter;
    }

    public CardPayOrJok getStatus(String text) {
        if (text.contains("Pay")) return CardPayOrJok.PAY;
        else if (text.equals("Joker")) return  CardPayOrJok.JOKER;
        else return CardPayOrJok.NONE;
    }
    // Method to reshuffle the cards
    public void reshuffleCards(List<String> cardAdditionalList) {
        // we make the general shuffle
        if (cardList.size() <= 4) {
            cardList = cardAdditionalList;
            // Indicating that the cards have been reshuffeled
            System.out.println("Cards have been given a reshuffle");

        }
        // we remove the cards that are already in the players list
        for (String i : player1.getCardLists()) {
            cardList.remove(i);
        }
        for (String i : player2.getCardLists()) {
            cardList.remove(i);
        }
    }

    enum CardPayOrJok  {
        PAY , JOKER, NONE
    }
}
