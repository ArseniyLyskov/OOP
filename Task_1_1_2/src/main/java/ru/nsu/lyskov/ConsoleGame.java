package ru.nsu.lyskov;

import java.util.Scanner;

/**
 * Класс с точкой входа - реализация блэкджека
 * через консоль для пользователя.
 */
public class ConsoleGame {
    /**
     * Точка входа программы.
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Type \"en\" to choose English language.");
        System.out.println("Введите \"ru\", чтобы выбрать Русский язык.");
        String localeName = in.nextLine();

        GameInterface gameInterface = new Blackjack(localeName);
        gameInterface.gameInit();
        gameInterface.gameOutput();

        while (true) {
            gameInterface.gameInput(in.nextLine());
            gameInterface.gameOutput();
        }
    }
}
