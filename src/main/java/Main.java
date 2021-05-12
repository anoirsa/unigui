import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
    GameStage gameStage;

    public static void main(String[] args) {
        launch(args);
    }

    //Global components
    //loginButton loggin;
    Label usernameLabel = new Label("Username:");
    TextField userNameTextField = new TextField();

    // Password
    Label passwordLabel = new Label("Password");
    PasswordField passwordField = new PasswordField();

    // loginButtons
    Button loginButton = new Button("Login");


    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Ono Card Game");

        //Components inits
        Hyperlink link = new Hyperlink();
        // Components configurations
          passwordField.setPromptText("Password");
          link.setText("Not registered ?");

        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();

        // GridPane configuration
        GridPane.setConstraints(usernameLabel,0,0);
        GridPane.setConstraints(userNameTextField,1,0);
        GridPane.setConstraints(passwordLabel,0,1);
        GridPane.setConstraints(passwordField,1,1);
        GridPane.setConstraints(loginButton,0,2);
        GridPane.setConstraints(link,1,2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getChildren().addAll(usernameLabel,
                userNameTextField,
                passwordLabel,
                passwordField,
                link,
                loginButton);

        // BorderPane configuration
        borderPane.setPadding(new Insets(10,10,10,10));
        borderPane.setCenter(gridPane);
        // Scene Configs
        Scene scene = new Scene(borderPane , 300,300);


        //loginButtons Actions ....

        link.setOnAction(e -> {
            RegisterStage.RegisterPane();
        });
        loginButton.setOnAction(event -> {
            gameStage = new GameStage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Credentials not correct");
            boolean exsit = RegisterStage.DB.stream().anyMatch(p -> p.getPassword().equals(passwordField.getText()) &&
                    p.getPlayerName().equals(userNameTextField.getText()));
            System.out.println(exsit);
            // For testing purpose
            if (!exsit) gameStage.MainGameStage();
            else alert.show();

        });

        window.setScene(scene);
        window.show();


    }
}
