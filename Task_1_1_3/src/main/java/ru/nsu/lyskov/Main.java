package ru.nsu.lyskov;

import ru.nsu.lyskov.Exceptions.DivisionByZeroException;
import ru.nsu.lyskov.Exceptions.IncorrectAssignmentException;

public class Main {
    public static void main(String[] args) {
        // Парсинг строки и создание объекта выражения
        String expressionStr = "(3+(2.67*x))";
        Expression eParsed = null;
        try {
            eParsed = Expression.parse(expressionStr);
        } catch (IncorrectAssignmentException exception) {
            exception.printStackTrace();
        }
        assert eParsed != null;
        eParsed.print();
        System.out.println();

        Expression e = new Add(new Number(3.4), new Mul(new Number(2), new Variable("x")));

        // Печать выражения
        e.print();
        System.out.println();

        // Символьное дифференцирование
        Expression de = e.derivative("x");
        de.print();
        System.out.println();

        // Вычисление значения выражения при x = 10
        double result = 0;
        try {
            result = e.eval("x = 10.43; y = 13");
        } catch (DivisionByZeroException | IncorrectAssignmentException exception) {
            exception.printStackTrace();
        }
        System.out.println(result);

        Expression test = new Div(new Number(1), new Number(5));
        double res = 0;
        try {
            res = test.eval("");
        } catch (DivisionByZeroException | IncorrectAssignmentException exception) {
            exception.printStackTrace();
        }
        System.out.println(res);

    }
}