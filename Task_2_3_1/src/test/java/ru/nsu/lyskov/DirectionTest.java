package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DirectionTest {
    @Test
    void testIsOpposite() {
        for (Direction direction1 : Direction.values()) {
            for (Direction direction2 : Direction.values()) {
                boolean isOpposite = direction1.isOpposite(direction2);
                System.out.print(direction1 + " ");
                System.out.print(isOpposite ? "is the opposite of" : "is not the opposite of");
                System.out.print(" " + direction2 + "\n");
            }
        }
        assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));
        assertFalse(Direction.UP.isOpposite(Direction.LEFT));
    }
}