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

/**
 * Тестовый класс для {@link GameView}. Проверяет корректность работы представления игры.
 */
public class GameViewTest extends ApplicationTest {
    // Настройка headless-режима для тестирования JavaFX
    static {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    private GameView gameView;
    private Label scoreLabel;
    private Label statusLabel;

    /**
     * Инициализация тестового окружения перед каждым тестом. Создает mock-объекты и экземпляр
     * GameView для тестирования.
     *
     * @param stage тестовое окно, предоставляемое TestFX
     */
    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas();
        scoreLabel = new Label();
        statusLabel = new Label();
        gameView = new GameView(canvas, scoreLabel, statusLabel);
    }

    /**
     * Вспомогательный метод для выполнения действий в потоке JavaFX. Гарантирует завершение
     * выполнения перед продолжением теста.
     *
     * @param action действие для выполнения в потоке JavaFX
     * @throws Exception если произошла ошибка при ожидании завершения
     */
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

    /**
     * Проверяет отображение статуса победы.
     */
    @Test
    public void testShowWinStatus() throws Exception {
        runAndWait(() -> gameView.showWinStatus());
        assertTrue(statusLabel.isVisible());
        assertTrue(statusLabel.getText().contains("SIGMA"));
    }

    /**
     * Проверяет отображение статуса поражения.
     */
    @Test
    public void testShowLoseStatus() throws Exception {
        runAndWait(() -> gameView.showLoseStatus());
        assertTrue(statusLabel.isVisible());
        assertTrue(statusLabel.getText().contains("NON SIGMA"));
    }

    /**
     * Проверяет сброс состояния представления.
     */
    @Test
    public void testReset() throws Exception {
        runAndWait(() -> gameView.reset());
        assertTrue(scoreLabel.getText().startsWith("0"));
        assertFalse(statusLabel.isVisible());
    }

    /**
     * Проверяет перерисовку игровой модели.
     */
    @Test
    public void testRedraw() throws Exception {
        GameModel mockModel = mock(GameModel.class);
        runAndWait(() -> gameView.redraw(mockModel));
        verify(mockModel).render(any());
    }
}