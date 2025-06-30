package ru.nsu.lyskov.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Objects;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ru.nsu.lyskov.Direction;

public class GameControllerTest extends ApplicationTest {
    static {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    private GameController controller;

    @BeforeAll
    public static void ensureHeadless() {

    }

    @Override
    public void start(Stage stage) {
        controller = new GameController();

        Button startButton = new Button("Start");
        Canvas gameCanvas = new Canvas(600, 400);
        Label scoreLabel = new Label();
        Label statusLabel = new Label();

        inject(controller, "startButton", startButton);
        inject(controller, "gameCanvas", gameCanvas);
        inject(controller, "scoreLabel", scoreLabel);
        inject(controller, "statusLabel", statusLabel);

        Scene scene = new Scene(new Group(gameCanvas, startButton, scoreLabel, statusLabel));
        stage.setScene(scene);

        controller.setScene(stage);
    }

    @Test
    public void testSetSceneInitializesGame() {
        assertNotNull(controller);
        String expected = "0 / " + ru.nsu.lyskov.Constants.L_WIN_SCORE;
        String actual = ((Label) Objects.requireNonNull(
                getField(controller, "scoreLabel"))).getText();
        assertEquals(expected, actual);
    }

    @Test
    public void testOnStartStartsGameLoop() {
        interact(controller::onStart);
        assertTrue((Boolean) getField(controller, "isRunning"));
    }

    @Test
    public void testKeyPress() {
        KeyEvent enterEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
                                           false, false, false, false
        );
        KeyEvent wEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "W", "W", KeyCode.W,
                                       false, false, false, false
        );

        interact(() -> {
            invokePrivateMethod(controller, "handleKeyPress", enterEvent);
        });

        interact(() -> {
            invokePrivateMethod(controller, "handleKeyPress", wEvent);
        });

        assertEquals(Direction.UP, getField(controller, "pendingDirection"));
    }

    private void invokePrivateMethod(Object target, String methodName, Object arg) {
        try {
            var method = target.getClass().getDeclaredMethod(methodName, KeyEvent.class);
            method.setAccessible(true);
            method.invoke(target, arg);
        } catch (Exception e) {
            fail("invokePrivateMethod failed: " + e.getMessage());
        }
    }

    private void inject(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            fail("Injection failed: " + e.getMessage());
        }
    }

    private Object getField(Object target, String fieldName) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            fail("Failed to access field: " + e.getMessage());
            return null;
        }
    }
}
