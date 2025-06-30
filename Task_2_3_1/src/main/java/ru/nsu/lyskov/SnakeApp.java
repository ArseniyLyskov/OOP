package ru.nsu.lyskov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.lyskov.controllers.GameController;

/**
 * Главный класс приложения "Змейка", наследующий от {@link Application}. Отвечает за запуск JavaFX
 * приложения и инициализацию основного окна.
 */
public class SnakeApp extends Application {

    /**
     * Точка входа для JavaFX приложения.
     *
     * @param primaryStage главное окно приложения, предоставляемое платформой JavaFX
     * @throws Exception если произошла ошибка при загрузке FXML-файла или инициализации
     *                   контроллера
     */
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

    /**
     * Основной метод приложения, запускающий JavaFX приложение.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        launch(args);
    }
}