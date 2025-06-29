package ru.nsu.lyskov.views.rendering;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    /**
     * Отрисовывает объект на указанном GraphicsContext
     *
     * @param gc контекст для отрисовки
     */
    void render(GraphicsContext gc);
}