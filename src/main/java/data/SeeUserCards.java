package data;

import data.enums.Purpose;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SeeUserCards {

    Stage window;
    List<ImageView> listOfImages;
    int howManyCardsLeft;

    public Integer MainSeeCards(List<String> listToShow , Purpose purpose) {
        AtomicInteger valueReturnd = new AtomicInteger();

        //Determining how many cards are left based on the size of the argument "listToShow"
        howManyCardsLeft = listToShow.size();
        //
        listOfImages = new ArrayList<>();
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("See Cards board");
        GridPane gridPane = new GridPane();
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        System.out.println(listToShow);
        listToShow.stream().forEach(c -> {
            try {
                // Validating the type of cards that will be shown
                boolean chooseOrSeeMe =
                        purpose.equals(Purpose.CHOOSE_PURPOSE) || purpose.equals(Purpose.SEE_PURPOSE);
                String questionOrReal = chooseOrSeeMe ? c : "WhatCard";

                Image image =  new Image(new FileInputStream("./Cards/"+questionOrReal+".png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(150);
                imageView.setFitWidth(100);
                GridPane.setConstraints(imageView, i.get(),j.get());
                i.getAndIncrement();
                gridPane.getChildren().add(imageView);
                listOfImages.add(imageView);
                if (i.get() % 5 == 0) {
                    j.getAndIncrement();
                    i.set(0);
                }
            } catch (FileNotFoundException e) {

            }
        });
        listOfImages.stream().forEach(im -> {
            im.setOnMouseEntered(e -> {
                im.setOpacity(0.5);
            });
            im.setOnMouseExited(event -> {
                im.setOpacity(1);

            });
            if (purpose.equals(Purpose.CHOOSE_PURPOSE)) {
                im.setOnMouseClicked(event -> {
                    System.out.println(listOfImages.indexOf(im));
                    valueReturnd.set(listOfImages.indexOf(im));
                    System.out.println(valueReturnd.get());
                    window.close();
                });
            }
        });
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
       // Scene scene = new Scene(gridPane,450,450);
       // Pre-Testing
       Scene scene = new Scene(returnedLayout(gridPane,purpose),450,450);
        window.setScene(scene);
        window.showAndWait();
        return valueReturnd.get();
    }

    BorderPane returnedLayout(GridPane gridPane , Purpose purpose) {
        Label headerLabel = new Label();
        Label footerLabel = new Label();
        BorderPane borderPane = new BorderPane();
        gridPane.setAlignment(Pos.CENTER);
        headerLabel.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(headerLabel);
        borderPane.setCenter(gridPane);
        switch (purpose) {
            case COMPUTER_SEE:
                headerLabel.setText("The computer cards are hidden and cannot be seen");
                footerLabel.setAlignment(Pos.BOTTOM_CENTER);
                borderPane.setBottom(footerLabel);
                footerLabel.setText("Cards left are :" + howManyCardsLeft);
                break;
            case CHOOSE_PURPOSE:
                headerLabel.setText("You need to choose one of these options you have.");
            case SEE_PURPOSE:
                headerLabel.setText("Here you can see all your cards");
        }
        return borderPane;
    }


}
