import data.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardMatch {
    public static List<String> matchStart() {
        List<String> dataCard = new ArrayList<>();
        String[] colors = {"Red", "Blue", "Yellow", "Green"};
        Integer[] numbers = new Integer[9];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }
        for (String i : colors) {
            for (Integer j : numbers) {
                dataCard.add(i + " " + j);
                dataCard.add(i + " " + j);
            }
        }
        String[] specialCards = {"Joker","Suspend","Demand"};
        int p = 0;

        for (String i : specialCards) {
            switch (i) {
                case "Joker" :
                    p = 2;
                    break;
                case "Suspend" :
                    p = 4;
                    break;
                default:
                    p = 4;
                    break;
            }
            for (int j =  0 ;  j< p ; j++) {
                dataCard.add(i);
            }
        }
        dataCard.stream().forEach(c -> {
            if (c.contains("1")) {
                dataCard.set(dataCard.indexOf(c),c.replace("1","Pay"));
            }
        });
        //System.out.println(dataCard);
        Collections.shuffle(dataCard);
        // Testing
        dataCard.set(dataCard.size() - 2,"Yellow Pay");
        // Testing
        return dataCard;
    }

    public static List<String>[] distibuteCards(List<String> list) {
        Random ran = new Random();
        List<String> player1Cards = new ArrayList<>();
        List<String> player2Cards = new ArrayList<>();
        for (int i = 0 ; i<7 ; i++) {
            int index = ran.nextInt(list.size());
            player1Cards.add(list.get(index));
            list.remove(index);
            int index2 = ran.nextInt(list.size());
            player2Cards.add(list.get(index2));
            list.remove(index2);
        }
        // Pre-Testing
        player1Cards.add("Demand");
        // Pre-Testing
        return new List[]{list, player1Cards, player2Cards};
    }


    public static Player whoStart(Player me , Player computer) {
        Random randomChose = new Random();
        int raChose = randomChose.nextInt(2);
        return List.of(me,computer).get(raChose);
    }


    public static void displayChoices(List<String> list, String order) {
        System.out.println("There are all the cards you have have");
        for (String i : list) {
            int choiceNumber = list.indexOf(i)+1;
            System.out.println("Number " + list.indexOf(i)+1 +" "+ i);
        }

    }
}
