import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SeeUserCards {

    Stage window;
    List<ImageView> listOfImages;
    List<String>  cards = CardMatch.distibuteCards(CardMatch.matchStart())[1];
    public Integer MainSeeCards(List<String> listToShow) {
        AtomicInteger valueReturnd = new AtomicInteger();
        listOfImages = new ArrayList<>();
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("See Cards board");
        GridPane gridPane = new GridPane();
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        System.out.println(cards);
        cards.stream().forEach(c -> {
            try {
                Image image = new Image(new FileInputStream("./Cards/"+c+".png"));

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
            im.setOnMouseClicked(event -> {
                System.out.println(listOfImages.indexOf(im));
                valueReturnd.set(listOfImages.indexOf(im));
                System.out.println(valueReturnd.get());
                window.close();
            });
        });
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(gridPane,450,450);
        window.setScene(scene);
        window.showAndWait();
        return valueReturnd.get();

    }


}
