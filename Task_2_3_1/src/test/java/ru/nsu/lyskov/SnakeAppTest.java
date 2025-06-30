package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SnakeAppTest extends ApplicationTest {
    static {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

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
