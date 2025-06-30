package ru.nsu.lyskov.views.rendering;

import javafx.scene.canvas.GraphicsContext;

/**
 * Интерфейс для объектов, поддерживающих отрисовку на графическом контексте.
 */
public interface Renderable {
    /**
     * Выполняет отрисовку объекта на указанном графическом контексте.
     *
     * @param gc графический контекст ({@link GraphicsContext}) для отрисовки, предоставляющий
     *           методы для рисования фигур, текста и изображений
     */
    void render(GraphicsContext gc);
}