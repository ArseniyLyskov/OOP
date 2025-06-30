package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Тестовый класс для приложения SnakeApp. Проверяет базовую функциональность запуска JavaFX
 * приложения.
 */
public class SnakeAppTest extends ApplicationTest {
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

    private Stage primaryStage;

    /**
     * Запускает приложение SnakeApp перед выполнением каждого теста.
     *
     * @param stage тестовый Stage, предоставляемый TestFX
     * @throws Exception если произошла ошибка при запуске приложения
     */
    @Override
    public void start(Stage stage) throws Exception {
        new SnakeApp().start(stage);
        primaryStage = stage;
    }

    /**
     * Проверяет, что главное окно приложения успешно отображается после запуска.
     */
    @Test
    public void testStageIsShowing() {
        assertTrue(primaryStage.isShowing());
    }
}