package ru.nsu.lyskov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.lyskov.controllers.GameController;

public class SnakeApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ru/nsu/lyskov/fxml/GameView.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.show();

        controller.setScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}