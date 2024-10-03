package ru.nsu.lyskov;

/**
 * Интерфейс, предоставляющий методы для
 * взаимодействия с классом игры.
 */
public interface GameInterface {
    /**
     * Инициализация игры.
     */
    void gameInit();

    /**
     * Ввод данных в игру.
     *
     * @param input Строка, которую классу игры необходимо обработать.
     */
    void gameInput(String input);

    /**
     * Вывод данных из игры.
     */
    void gameOutput();
}
