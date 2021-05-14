import data.*;
import data.enums.Order;
import data.enums.Purpose;
import data.enums.Status;
import data.enums.Who;
import data.listpackage.Situation;
import data.staticmethods.Outsiders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

     // outsiders
    StringBuilder analyzer;
    String[] splitFirstOrder;
    ObservableList tableObserve;
    TableView<Situation> tableView;

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
         //Analyze
        tableObserve.add(Outsiders.analyzeOutcome(nextOrder));

         //------
         proceed.setOnAction( e-> {
             nextOrder = me.handlePlayerChoices.apply(nextOrder);
             //Analyze
             tableObserve.add(Outsiders.analyzeOutcome(nextOrder));
             //-------
             nextOrder = computer.handlePlayerChoices.apply(nextOrder);
             //Analyze
             tableObserve.add(Outsiders.analyzeOutcome(nextOrder));
             //-------
             referee.setPlayer1(me);
             referee.setPlayer2(computer);
             nextOrder = referee.handleOutcome.apply(nextOrder);
             // Analyze
             tableObserve.add(Outsiders.analyzeOutcome(nextOrder));
             //--------
             gameEnded = referee.handEndOfTheGame.test(listi);
             me = referee.getPlayer1();
             computer = referee.getPlayer2();
             // Setting the updated field image
             imageV = seeCurrentCard(Outsiders.splitNextOrder(nextOrder));
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

        //launch the observable list
        tableObserve = FXCollections.observableArrayList();

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
        analyzer = new StringBuilder();
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
        handlingTheTableView();
        Scene scene = new Scene(pani,900,450);
        window.setScene(scene);
        window.showAndWait();
    }

    public void handlingTheTableView() {
        TableColumn<Situation, String> playerNameColumn =
                new TableColumn<>("Player name");
        TableColumn<Situation, Order> orderColumn =
                new TableColumn<>("Order");
        TableColumn<Situation , String> cardPfieldColumn =
                new TableColumn<>("Card on field");
        TableColumn<Situation , String> cardInputedColumn =
                new TableColumn<>("Next Card");
        playerNameColumn.setMinWidth(100);
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        orderColumn.setMinWidth(100);
        orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));

        cardPfieldColumn.setMinWidth(100);
        cardPfieldColumn.setCellValueFactory(new PropertyValueFactory<>("cardPfield"));

        cardInputedColumn.setMinWidth(100);
        cardInputedColumn.setCellValueFactory(new PropertyValueFactory<>("cardInputed"));

        tableView = new TableView<>();
        tableView.setItems(tableObserve);
        tableView.getColumns().addAll(playerNameColumn,orderColumn,
                                      cardPfieldColumn, cardInputedColumn);
        pani.setLeft(tableView);
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
