package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompositeCheckerTest {
    @Test
    void illegalArgumentExceptionTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CompositeChecker.sequentialCompositeCheck(new int[]{2, 1, 0})
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> CompositeChecker.sequentialCompositeCheck(new int[]{-2})
        );
    }

    @Test
    void compositeFindingCheck() {
        assertTrue(CompositeChecker.streamCompositeCheck(new int[]{2, 3, 4, 7}));
        assertTrue(CompositeChecker.threadCompositeCheck(new int[]{2, 3, 4, 7}));
        assertTrue(CompositeChecker.threadCompositeCheck(new int[]{2, 3, 4, 7}, 3));
        assertTrue(CompositeChecker.sequentialCompositeCheck(new int[]{2, 3, 4, 7}));

        assertFalse(CompositeChecker.streamCompositeCheck(new int[]{2, 3, 5, 7}));
        assertFalse(CompositeChecker.threadCompositeCheck(new int[]{2, 3, 5, 7}));
        assertFalse(CompositeChecker.threadCompositeCheck(new int[]{2, 3, 5, 7}, 3));
        assertFalse(CompositeChecker.sequentialCompositeCheck(new int[]{2, 3, 5, 7}));
    }

}
