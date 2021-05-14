import data.*;
import data.enums.Purpose;
import data.enums.Status;
import data.enums.Who;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameStage {

     List <ImageView> listOfImages;
     List<String>  cards = CardMatch.distibuteCards(CardMatch.matchStart())[1];
     Stage window;
     Button proceed,seeCards,seeComputerCards;
     Image imageV;
     ImageView imageOfInput ;
     SeeUserCards seeUserCards;
     BorderPane pani;
     VBox vbox;

     // Inside the game
     Player me,computer;
     String nextOrder, cureFirstOrder;
     Referee referee;
     boolean gameEnded;

     // outsider
    String[] splitFirstOrder;
    public GameStage( ) {

    }

    public void MainGameStage()   {
        start();
        List<String> listi = CardMatch.matchStart();
        List<String>[] lists = CardMatch.distibuteCards(listi);
        // We initiate the player

        me = new Player("Anouar", Status.OFF, lists[1], Who.USER);
        // We initiate the computer
        computer = new Player("Computer", Status.OFF, lists[2], Who.COMPUTER );

        List<String> remainigCards = lists[0];
        if(CardMatch.whoStart(me,computer).getPlayerName().equals(me.getPlayerName())) {
            me.setStatus(Status.TURN);
        }
        else {
            computer.setStatus(Status.TURN);
        }

         nextOrder = "";
         referee = new Referee(me,computer,remainigCards,nextOrder);
         gameEnded = false;
         nextOrder = referee.getFirstCard.get();

         proceed.setOnAction( e-> {

             nextOrder = me.handlePlayerChoices.apply(nextOrder);
             nextOrder = computer.handlePlayerChoices.apply(nextOrder);
             referee.setPlayer1(me);
             referee.setPlayer2(computer);
             nextOrder = referee.handleOutcome.apply(nextOrder);
             gameEnded = referee.handEndOfTheGame.test(listi);
             me = referee.getPlayer1();
             computer = referee.getPlayer2();
             // Setting the updated field image
             imageV = seeCurrentCard(cureFirstOrder);
             imageOfInput.setImage(imageV);
             // Setting the updated field image
         });

         // When the game ends the procced buttom must be disabled
         proceed.setDisable(gameEnded);

         end();
    }

    public  void errorStage ()  {
        window = new Stage();
        window.setTitle("Credentails do not match");
        StackPane pan = new StackPane();

        Scene scene = new Scene(pan,200,200);
        window.setScene(scene);
        window.show();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        window.close();
    }

    public void start() {
        //seeUserCards = new SeeUserCards();
        listOfImages = new ArrayList<>();
        imageOfInput = new ImageView();
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Board");

        // Buttons Config
        proceed = new Button("Proceed");
        seeCards = new Button("See your cards :");
        seeComputerCards = new Button("See the computer cards");
        seeUserCards = new SeeUserCards();
        seeCards.setOnAction(e -> {
            seeUserCards.MainSeeCards(me.getCardLists(),
                                      Purpose.SEE_PURPOSE);
        });
        seeComputerCards.setOnAction(e -> {
            seeUserCards.MainSeeCards(computer.getCardLists() ,
                    Purpose.COMPUTER_SEE);
        });
        pani = new BorderPane();
        vbox = new VBox();
    }

    public void end() {
        // handle displaying first inputed card
        splitFirstOrder = nextOrder.split("-");
        cureFirstOrder = splitFirstOrder[0];
        imageV = seeCurrentCard(cureFirstOrder);

        // Setting the first random image view

        imageOfInput.setImage(imageV);
        // Setting the dimensions of the image
        imageOfInput.setFitHeight(210);
        imageOfInput.setFitWidth(180);

        // handle displaying first inputed card
        vbox.getChildren().addAll(seeComputerCards,proceed,new
                Label("The card on field is :"),
                 imageOfInput ,
                seeCards);

        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        pani.setCenter(vbox);
        Scene scene = new Scene(pani,450,450);
        window.setScene(scene);
        window.showAndWait();
    }

    public  Image seeCurrentCard(String imageName)  {
        try {
            Image image = new Image(new FileInputStream("./Cards/"+imageName+".png"));
            return image;

        }catch (FileNotFoundException e) {
            System.out.println("The searched file is not yet found");
            return null;
        }

    }


}
