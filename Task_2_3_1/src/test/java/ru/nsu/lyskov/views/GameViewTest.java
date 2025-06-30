package ru.nsu.lyskov.views;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ru.nsu.lyskov.models.GameModel;

public class GameViewTest extends ApplicationTest {

    private GameView gameView;
    private Canvas canvas;
    private Label scoreLabel;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas();
        scoreLabel = new Label();
        statusLabel = new Label();
        gameView = new GameView(canvas, scoreLabel, statusLabel);
    }

    private void runAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void testShowWinStatus() throws Exception {
        runAndWait(() -> gameView.showWinStatus());
        assertTrue(statusLabel.isVisible());
        assertTrue(statusLabel.getText().contains("SIGMA"));
    }

    @Test
    public void testShowLoseStatus() throws Exception {
        runAndWait(() -> gameView.showLoseStatus());
        assertTrue(statusLabel.isVisible());
        assertTrue(statusLabel.getText().contains("NON SIGMA"));
    }

    @Test
    public void testReset() throws Exception {
        runAndWait(() -> gameView.reset());
        assertTrue(scoreLabel.getText().startsWith("0"));
        assertFalse(statusLabel.isVisible());
    }

    @Test
    public void testRedraw() throws Exception {
        GameModel mockModel = mock(GameModel.class);
        runAndWait(() -> gameView.redraw(mockModel));
        verify(mockModel).render(any());
    }
}
