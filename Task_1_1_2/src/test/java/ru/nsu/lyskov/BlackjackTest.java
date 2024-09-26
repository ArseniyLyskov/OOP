package ru.nsu.lyskov;

import java.util.Random;
import org.junit.jupiter.api.Test;

class BlackjackTest {
    private static final int testsCount = 100;
    private static final int maxNumberOfMoves = 50;
    private final Random random = new Random();

    @Test
    void runTests() {
        Blackjack blackjack;
        for (int i = 0; i < testsCount; i++) {
            int numberOfMoves = random.nextInt(maxNumberOfMoves + 1);
            if (random.nextInt(2) > 0) {
                blackjack = new Blackjack("ru");
            } else {
                blackjack = new Blackjack("en");
            }
            blackjack.gameInit();
            blackjack.gameOutput();

            for (int j = 0; j < numberOfMoves; j++) {
                String decision = String.valueOf(random.nextInt(2));
                blackjack.gameInput(decision);
                System.out.println(decision);
                blackjack.gameOutput();
            }
        }
    }
}