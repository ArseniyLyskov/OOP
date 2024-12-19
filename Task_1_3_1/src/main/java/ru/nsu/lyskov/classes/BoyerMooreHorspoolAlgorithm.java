package ru.nsu.lyskov.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ru.nsu.lyskov.interfaces.BufferInterface;

public class BoyerMooreHorspoolAlgorithm {
    private final List<Integer> result = new ArrayList<>();
    private final BufferInterface<Character> buffer;
    private final String target;
    private final int targetLength;
    private final HashMap<Character, Integer> shiftTable = new HashMap<>();
    private int totalShift = 0;

    public BoyerMooreHorspoolAlgorithm(BufferInterface<Character> buffer, String target) {
        this.buffer = buffer;
        this.target = target;
        targetLength = target.length();
        buildShiftTable();
    }

    public int getStringPatternShift() {
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
