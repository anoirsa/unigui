package data;

import data.enums.Order;
import data.enums.Purpose;
import data.enums.Who;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Helper {
    private String playerName;
    private Who whoPlayer;
    Scanner scanner = new Scanner(System.in);
    SeeUserCards seeUserCards = new SeeUserCards();
    public Helper(String playerName, Who whoPlayer)  {
        this.playerName = playerName;
        this.whoPlayer = whoPlayer;
    }
    // Method to handle demand card
    Function<Integer, String> colorNumber = number -> {
        String returnedValue;
        switch (number) {
            case 1 :
                returnedValue = "Red CX";
                break;
            case 2 :
                returnedValue = "Yellow CX";
                break;
            case 3 :
                returnedValue = "Blue CX";
                break;
            case 4 :
                returnedValue = "Green CX";
                break;
            default :
                returnedValue = "NON_VALID CX";
                break;
        }
        return returnedValue;
    };
    public String displayChoices(List<String> list, String cuurentCard , Order order ) throws InterruptedException {
        if (whoPlayer.equals(Who.USER)) {
            System.out.println("It is your turn");

            //TimeUnit.MILLISECONDS.sleep(500);

            System.out.println("These are all the cards you have");
            for (String i : list) {
                int choiceNumber = list.indexOf(i)+1;
                System.out.println(choiceNumber +"- "+ i);
                //TimeUnit.MILLISECONDS.sleep(500);
            }
        }
        else {
            System.out.println("It is the computer turn");
             // Troubleshoot
            for (String i : list) {
                int choiceNumber = list.indexOf(i)+1;
                System.out.println(choiceNumber +"- "+ i);
                //TimeUnit.MILLISECONDS.sleep(500);
            }
            //---------
        }
        //TimeUnit.SECONDS.sleep(1);
        //DELAY
        System.out.println("The current card on field is : " + cuurentCard);

        //TimeUnit.SECONDS.sleep(1);
        // DELAY

        return order.equals(Order.PICK_CARDS) ? getAvailablePickCardC(list,cuurentCard)
                : getAvailableChocies(list, cuurentCard);

    }

    public  String getAvailablePickCardC(List<String> list, String cuurentCard) {
        List<String> collect = list.stream().filter(c -> JS.test(c)).collect(Collectors.toList());
        int listSize = collect.size();
        return listSize > 0 ? displayAvailableChoices.apply(collect, list.size()) : "NIP";
    }

    public  String getAvailableChocies (List<String> list, String cuurentCard) {
        if (!JSD(cuurentCard)) {
            String[] cardSections = cuurentCard.split(" ");
            List<String> collect = list.stream().filter(c -> c.contains(cardSections[0]) ||
                    c.contains(cardSections[1]) ||
                    JSD(c))
                    .collect(Collectors.toList());
            int listSize = collect.size();
            return listSize > 0 ? displayAvailableChoices.apply(collect,list.size()) : "NIP";
        }
        else return  list.size() >0 ? displayAvailableChoices.apply(list , list.size()) : "NIP";
    }

    public  boolean JSD(String p) {
        return p.equals("Joker") || p.equals("Suspend") || p.equals("Demand");
    }
    // indicate if card is payee
    Predicate<String> JS = (card) -> card.equals("Joker") || card.contains("Pay");
    // function to display and choose all the current available card options
    BiFunction<List<String>,Integer, String> displayAvailableChoices = (list , cardsRemained) -> {
        try {
            int myIndexChoice;
            String demandedColor, demandedColorGUI;
            String theFinalReturn;
            List<String> givenList = (ArrayList)((ArrayList<String>) list).clone();

            //list.add("doNotPlay");
            if (whoPlayer.equals(Who.USER)) {
                givenList.add("doNotPlay");
                System.out.println("Your available choices are :");
                //TimeUnit.SECONDS.sleep(1);
                //Delay
                givenList.forEach(c ->{
                    System.out.println(givenList.indexOf(c) + 1 + "- " + c);
                    /**
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } **/
                });
                //TimeUnit.SECONDS.sleep(1);
                //Delay
                //IMPROVE
                //Integer myChoice = scanner.nextInt();
                Integer myChoice  = seeUserCards.MainSeeCards(givenList, Purpose.CHOOSE_PURPOSE);
                if (myChoice == ( givenList.size() - 1 )) {
                    System.out.println("You decided to not choose any option");
                    return "NIP";
                }
                //IMPROVE
                myIndexChoice = myChoice ;
                // Demand handling part //
                if (givenList.get(myIndexChoice).equals("Demand")) {
                //  System.out.println("What color you are demanding \n 1-Red \n 2-Yellow \n 3-Blue \n 3-Green");
                    TimeUnit.SECONDS.sleep(1);
                    // Delay
                    List<String> listOfColorDemanded = List.of("Green CX","Red CX","Yellow CX","Blue CX");
                    // Using the GUI for demand color
                    Integer DemandedReference = seeUserCards.MainSeeCards
                            (listOfColorDemanded, Purpose.CHOOSE_PURPOSE);
                    demandedColorGUI = colorNumber.apply(DemandedReference + 1);
                    //demandedColor = colorNumber.apply(scanner.nextInt());
                    /**while (demandedColor.equals("NON_VALID CX")) {
                        System.out.println("Your input must be a number between 1 and 4");
                        demandedColor = colorNumber.apply(scanner.nextInt());

                    } **/
                    theFinalReturn = demandedColorGUI;
                } else theFinalReturn = givenList.get(myIndexChoice);
            } else {

                Random random = new Random();
                myIndexChoice = random.nextInt(givenList.size());
                System.out.println(myIndexChoice);
                // Troubleshoot
                if (givenList.get(myIndexChoice).equals("Demand")) {
                    demandedColor = colorNumber.apply(ThreadLocalRandom.current().nextInt(1, 4 +1 ));
                    System.out.println("The computer has demanded " + demandedColor);
                    theFinalReturn = demandedColor;

                } else {
                    theFinalReturn = givenList.get(myIndexChoice);
                }
            }
            System.out.println("The inputed/demanded card is .. " + theFinalReturn);
            return theFinalReturn;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    };
}
