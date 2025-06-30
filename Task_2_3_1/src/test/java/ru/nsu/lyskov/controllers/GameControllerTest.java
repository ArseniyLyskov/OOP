package ru.nsu.lyskov.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ru.nsu.lyskov.Direction;

public class GameControllerTest extends ApplicationTest {
    private GameController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        controller = new GameController();

        Button startButton = new Button("Start");
        Canvas gameCanvas = new Canvas(600, 400);
        Label scoreLabel = new Label();
        Label statusLabel = new Label();

        inject(controller, "startButton", startButton);
        inject(controller, "gameCanvas", gameCanvas);
        inject(controller, "scoreLabel", scoreLabel);
        inject(controller, "statusLabel", statusLabel);

        Scene scene = new Scene(
                new javafx.scene.Group(gameCanvas, startButton, scoreLabel, statusLabel));
        stage.setScene(scene);
        stage.show();

        controller.setScene(stage);
    }

    @Test
    public void testSetSceneInitializesGame() {
        assertNotNull(controller);
        assertEquals("0 / " + ru.nsu.lyskov.Constants.L_WIN_SCORE,
                     ((Label) Objects.requireNonNull(getField(controller, "scoreLabel"))).getText()
        );
    }

    @Test
    public void testOnStartStartsGameLoop() {
        interact(() -> controller.onStart());
        assertTrue((Boolean) getField(controller, "isRunning"));
    }

    @Test
    public void testKeyPress() {
        KeyEvent enterEvent =
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
                             false, false, false, false
                );

        interact(() -> {
            try {
                var method =
                        GameController.class.getDeclaredMethod("handleKeyPress", KeyEvent.class);
                method.setAccessible(true);
                method.invoke(controller, enterEvent);
            } catch (Exception ignored) {
            }
        });

        KeyEvent wEvent =
                new KeyEvent(KeyEvent.KEY_PRESSED, "W", "W", KeyCode.W,
                             false, false, false, false
        );

        interact(() -> {
            try {
                var method =
                        GameController.class.getDeclaredMethod("handleKeyPress", KeyEvent.class);
                method.setAccessible(true);
                method.invoke(controller, wEvent);
            } catch (Exception ignored) {
            }
        });

        assertEquals(Direction.UP, getField(controller, "pendingDirection"));
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
