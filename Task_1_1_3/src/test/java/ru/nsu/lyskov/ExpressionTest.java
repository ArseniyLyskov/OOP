package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.exceptions.DivisionByZeroException;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;
import ru.nsu.lyskov.exceptions.IncorrectExpressionException;

class ExpressionTest {

    /**
     * Тест на вычисление простого выражения: 3 + 5.
     */
    @Test
    void testSimpleAdd() throws IncorrectAssignmentException, DivisionByZeroException {
        Expression expr = new Add(new Number(3), new Number(5));
        assertEquals(8.0, expr.eval(new HashMap<>()));
    }

    /**
     * Тест на дифференцирование суммы: (x + 5)' = 1.
     */
    @Test
    void testDerivativeAdd() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        Expression derivative = expr.derivative("x");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        derivative.print(new PrintStream(out));
        assertEquals("(1+0)", out.toString());
    }

    /**
     * Тест на вычисление произведения: 3 * 5.
     */
    @Test
    void testSimpleMul() throws IncorrectAssignmentException, DivisionByZeroException {
        Expression expr = new Mul(new Number(3), new Number(5));
        assertEquals(15.0, expr.eval(new HashMap<>()));
    }

    /**
     * Тест на вычисление выражения с переменной: 2 * x при x = 4.
     */
    @Test
    void testVariableEvaluation() throws IncorrectAssignmentException, DivisionByZeroException {
        Expression expr = new Mul(new Number(2), new Variable("x"));
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 4.0);
        assertEquals(8.0, expr.eval(variables));
    }

    /**
     * Тест на дифференцирование произведения: (2 * x)' = 2.
     */
    @Test
    void testDerivativeMul() {
        Expression expr = new Mul(new Number(2), new Variable("x"));
        Expression derivative = expr.derivative("x");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        derivative.print(new PrintStream(out));
        assertEquals("((0*x)+(2*1))", out.toString());
    }

    /**
     * Тест на вычисление деления: 10 / 2 = 5.
     */
    @Test
    void testSimpleDiv() throws DivisionByZeroException, IncorrectAssignmentException {
        Expression expr = new Div(new Number(10), new Number(2));
        assertEquals(5.0, expr.eval(new HashMap<>()));
    }

    /**
     * Тест на деление на ноль, должно выбросить DivisionByZeroException.
     */
    @Test
    void testDivisionByZero() {
        Expression expr = new Div(new Number(10), new Number(0));
        assertThrows(DivisionByZeroException.class, () -> expr.eval(new HashMap<>()));
    }

    /**
     * Тест на разбор строки и вычисление: (3 + (2 * x)) при x = 5.
     */
    @Test
    void testExpressionParsing() throws
                                 IncorrectAssignmentException, DivisionByZeroException,
                                 IncorrectExpressionException {
        String input = "(3+(2*x))";
        Expression expr = Expression.parse(input);
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 5.0);
        assertEquals(13.0, expr.eval(variables));
    }

    /**
     * Тест на парсинг строки с ошибкой в выражении.
     */
    @Test
    void testIncorrectExpressionParsing() {
        String input = "(3+(2*))";  // Некорректное выражение
        assertThrows(IncorrectExpressionException.class, () -> Expression.parse(input));
    }

    /**
     * Тест на некорректное присваивание переменных.
     */
    @Test
    void testIncorrectAssignmentParsing() throws
                                          IncorrectExpressionException, DivisionByZeroException,
                                          IncorrectAssignmentException {
        Expression expression = Expression.parse("(1.2+(3.4-(5.6*(7.8/9.0))))");
        Expression derivative = expression.derivative("");
        expression.print(new PrintStream(new ByteArrayOutputStream()));
        derivative.print(new PrintStream(new ByteArrayOutputStream()));
        assertEquals(-0.253333, expression.eval(""), 1e-6);
        assertThrows(IncorrectAssignmentException.class, () -> expression.eval("di8:u2h&3"));
    }

    /**
     * Тест на корректное преобразование переменных из строки и вычисление: 3 + (2.8 * x), при x =
     * 10.2.
     */
    @Test
    void testEvalWithVariablesString() throws
                                       DivisionByZeroException, IncorrectAssignmentException {
        Expression expr = new Add(
                new Number(3),
                new Mul(new Number(2.8), new Variable("x"))
        );
        assertEquals(31.56, expr.eval("x = 10.2"), 1e-6);
    }

    /**
     * Тест на печать выражения: (3 + (2 * x)).
     */
    @Test
    void testPrintExpression() {
        Expression expr = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        expr.print(new PrintStream(out));
        assertEquals("(3+(2*x))", out.toString());
    }
}