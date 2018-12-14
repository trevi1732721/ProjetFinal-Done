package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene currentScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calcul Matriciel");
        Controller nouveau = new Controller();
        currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
        primaryStage.show();
        primaryStage.setResizable(true);
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
