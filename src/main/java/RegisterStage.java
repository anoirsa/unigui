import data.enums.Gender;
import data.Player;
import data.enums.Who;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static data.enums.Gender.*;


public class RegisterStage {

    // For later
    static PasswordField passwordField = new PasswordField();
    static PasswordField repeatPasswordField = new PasswordField();
    // public static List<>

    // Global components
    static Button registerButton;
    Button cancelButton;

    //Fake Database
    public static List<Player> DB = new ArrayList<>();

    public static void RegisterPane() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Register");

        // Local components
        Label userNameLabel = new Label("username : ");
        Label passwordLabel = new Label("password : ");
        Label repeatPasswordLabel = new Label("Type your password again : ");
        Label email = new Label("Email : ");
        Label gender = new Label("Gender : ");

        // Special components
        ChoiceBox<Gender> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(MALE, FEMALE,PREFER_NOT_TO_SAY);

        // Input TextFields
        TextField usernNameField = new TextField();
        TextField emailField = new TextField();
        Label outputLabel = new Label();

        // Buttons
        registerButton = new Button("Register");
        //


        // Confugrations
        passwordField.setPromptText("Type password");
        repeatPasswordField.setPromptText("Type password again");
        choiceBox.setValue(MALE);

        // Actions
        repeatPasswordField.setOnKeyTyped(e->{
          outputLabel.setText(validate()
                  ? "Passwords match" : "Passwords do not match");
      });
      passwordField.setOnKeyTyped(e ->{
          outputLabel.setText(validate()
                  ? "Passwords match" : "Passwords do not match" );
      });

      // Alert init
        Alert  a = new Alert(Alert.AlertType.NONE);

      registerButton.setOnAction( e -> {
          boolean insert = registerHandler(usernNameField, emailField, choiceBox);
          System.out.println(insert);
          if (insert) {
              a.setAlertType(Alert.AlertType.CONFIRMATION);
              a.setContentText("Player registered");
              a.show();
              try {
                  TimeUnit.SECONDS.sleep(2);
                  window.close();
              } catch (InterruptedException interruptedException) {
                  interruptedException.printStackTrace();
              }

          } else {
             a.setAlertType(Alert.AlertType.ERROR);
             a.setContentText("Error check details");
             a.show();
          }
      });

        GridPane grid = new GridPane();

        // Pans constraints
        GridPane.setConstraints(userNameLabel,0,0);
        GridPane.setConstraints(usernNameField,1,0);
        GridPane.setConstraints(passwordLabel,0,1);
        GridPane.setConstraints(passwordField,1,1);
        GridPane.setConstraints(repeatPasswordLabel,0,2);
        GridPane.setConstraints(repeatPasswordField,1,2);
        GridPane.setConstraints(email,0,3);
        GridPane.setConstraints(emailField,1,3);
        GridPane.setConstraints(gender,0,4);
        GridPane.setConstraints(choiceBox,1,4);
        GridPane.setConstraints(registerButton,0,5);
        GridPane.setConstraints(outputLabel,0,6);

        // Pans Configs
        grid.getChildren().addAll(userNameLabel,usernNameField,passwordLabel,passwordField,
                                 repeatPasswordField, repeatPasswordLabel, email, emailField, gender,
                                  choiceBox, registerButton,outputLabel);
        grid.setVgap(10);
        grid.setHgap(10);

        Scene scene = new Scene(grid, 300,300);

        window.setOnCloseRequest(e -> {
            window.close();
        });
        window.setScene(scene);
        window.showAndWait();
    }

    public static boolean validate() {
        return passwordField.getText().equals(repeatPasswordField.getText());
    }

    public static boolean registerHandler(TextField userTextField ,
                                 TextField emailTextField, ChoiceBox genderBox ) {
        boolean isValid = !userTextField.getText().trim().isEmpty() && validate() &&
                          !emailTextField.getText().trim().isEmpty();
        if (isValid) {
            Player player = new Player(userTextField.getText(),null,Who.USER, null,
                    Gender.valueOf(genderBox.getValue().toString()),emailTextField.getText(),
                    passwordField.getText());
            boolean b = DB.stream().map(p -> p.getPlayerName()).anyMatch(p -> p.equals(player.getPlayerName()));
            if (!b) DB.add(player);
            return true;
        }
        else return false;
    }
}
