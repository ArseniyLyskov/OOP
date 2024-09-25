package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

public class TestGame {
    @Test
    void testGame() {
        GameInterface gameInterface = new Blackjack("ru");
        gameInterface.gameInit();
        gameInterface.gameOutput();
        gameInterface.gameInput("1");
        gameInterface.gameOutput();

        gameInterface.gameInput("1");
        gameInterface.gameOutput();

        gameInterface.gameInput("0");
        gameInterface.gameOutput();
    }
}
