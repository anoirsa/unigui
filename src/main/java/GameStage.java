import data.Player;
import data.Status;
import data.Who;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GameStage {

     List <ImageView> listOfImages;
     List<String>  cards = CardMatch.distibuteCards(CardMatch.matchStart())[1];
     Stage window;
     Button seeCards;
     Button proceed;
     Image imageV;
     ImageView imageOfInput;
     SeeUserCards seeUserCards;
     BorderPane pani;
     VBox vbox;

    public GameStage( ) {

    }

    public void MainGameStage()   {
        start();
        List<String> listi = CardMatch.matchStart();
        List<String>[] lists = CardMatch.distibuteCards(listi);
        // We initiate the player
        Player me = new Player("Anouar", Status.OFF, lists[1], Who.USER );
        // We initiate the computer
        Player computer = new Player("Computer", Status.OFF, lists[2], Who.COMPUTER );

        if(CardMatch.whoStart(me,computer).getPlayerName().equals(me.getPlayerName())) {
            me.setStatus(Status.TURN);
        }
        else {
            computer.setStatus(Status.TURN);
        }

        seeCards.setDisable(me.getStatus().equals(Status.OFF));

        seeCards.setOnAction( e-> {
            seeUserCards.MainSeeCards(lists[1]);
        });

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
        seeUserCards = new SeeUserCards();
        listOfImages = new ArrayList<>();
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Board");

        // Buttons Config
        seeCards = new Button("See cards");
        proceed = new Button();
        pani = new BorderPane();
        vbox = new VBox();
    }

    public void end() {

        vbox.getChildren().add(seeCards);
        vbox.setAlignment(Pos.CENTER);
        pani.setCenter(vbox);
        Scene scene = new Scene(pani,450,450);
        window.setScene(scene);
        window.showAndWait();
    }


}
