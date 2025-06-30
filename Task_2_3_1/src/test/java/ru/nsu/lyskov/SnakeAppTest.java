package ru.nsu.lyskov;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SnakeAppTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        new SnakeApp().start(stage);
        primaryStage = stage;
    }

    @Test
    public void testStageIsShowing() {
        assertTrue(primaryStage.isShowing());
    }
}
