package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.exceptions.DivisionByZeroException;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;
import ru.nsu.lyskov.exceptions.IncorrectExpressionException;

public class AdditionalTaskTest {

    /**
     * Считывание выражения со строки, работающее со строкой без скобок вокруг каждого выражения.
     */
    @Test
    void test1() throws IncorrectExpressionException, DivisionByZeroException,
                        IncorrectAssignmentException {
        String input = "1 - xy * (b /a ) + 2.760";
        Expression expr = Expression.parse(input);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        expr.print(new PrintStream(out));
        assertEquals("((1-(xy*(b/a)))+2.76)", out.toString());
        assertEquals(2.76, expr.eval("b = 6; xy = 0.5; a = 3"));
    }

    /**
     * Если выражение можно вычислить без какого-либо означивания (т.е. в нем нет переменных), то
     * выражение заменяется на результат вычисления.
     */
    @Test
    void test2a() throws IncorrectExpressionException, DivisionByZeroException {
        String input = "1.2 + 3.4 - 5.6 * (4 / 2)";
        Expression expr = Expression.parse(input);
        Expression expected = new Number(-6.6);
        assertEquals(expected, expr.simplify());
    }

    /**
     * Если выражение представляет из себя умножение на 0, то оно заменяется на константу 0.
     */
    @Test
    void test2b() throws IncorrectExpressionException, DivisionByZeroException {
        String input = "1.2 + 3.4 - 5.6 * (4 / 2) * 0 * abc";
        Expression expr = Expression.parse(input);
        Expression expected = new Number(4.6);
        assertEquals(expected, expr.simplify());
    }

    /**
     * Если выражение представляет из себя умножение на 1, то оно заменяется на второй множитель.
     */
    @Test
    void test2c() throws IncorrectExpressionException, DivisionByZeroException {
        String input = "1 * qwerty";
        Expression expr = Expression.parse(input);
        Expression expected = new Variable("qwerty");
        assertEquals(expected, expr.simplify());
    }

    /**
     * Если выражение представляет из себя вычитание двух одинаковых подвыражений, то оно
     * заменяется на константу 0.
     */
    @Test
    void test2d() throws IncorrectExpressionException, DivisionByZeroException {
        String input = "1 * qwerty - 1 * qwerty";
        Expression expr = Expression.parse(input);
        Expression expected = new Number(0);
        assertEquals(expected, expr.simplify());
    }
}
