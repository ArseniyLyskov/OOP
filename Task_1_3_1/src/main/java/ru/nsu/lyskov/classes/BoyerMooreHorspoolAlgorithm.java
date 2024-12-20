package ru.nsu.lyskov.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ru.nsu.lyskov.interfaces.BufferInterface;

public class BoyerMooreHorspoolAlgorithm {
    private final List<Integer> result = new ArrayList<>();
    private final String target;
    private final int targetLength;
    private final HashMap<Character, Integer> shiftTable = new HashMap<>();
    private int totalShift = 0;

    public BoyerMooreHorspoolAlgorithm(String target) {
        targetLength = target.length();
        if (targetLength == 0) {
            throw new IllegalArgumentException("You cannot search for an empty substring.");
        }
        this.target = target;
        buildShiftTable();
    }

    public int getStringPatternShift(BufferInterface<Character> buffer) {
        if (buffer.getCapacity() < targetLength) {
            throw new IllegalArgumentException(
                    "The buffer size is less than the size of the searched substring.");
        }

        if (buffer.getSize() < targetLength) {
            totalShift += buffer.getSize();
            return buffer.getSize();
        }

        int i = targetLength - 1;
        while (i >= 0 && buffer.peek(i) == target.charAt(i)) {
            i--;
        }
        if (i < 0) {
            result.add(totalShift);
        }

        int shift = shiftTable.getOrDefault(buffer.peek(targetLength - 1), targetLength);
        totalShift += shift;
        return shift;
    }

    public List<Integer> getResult() {
        return result;
    }

    private void buildShiftTable() {
        for (int i = 0; i < targetLength - 1; i++) {
            shiftTable.put(target.charAt(i), targetLength - 1 - i);
        }
    }
}
