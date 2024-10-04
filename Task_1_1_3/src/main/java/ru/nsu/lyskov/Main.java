package ru.nsu.lyskov;

public class Main {
    public static void main(String[] args) {
        // Парсинг строки и создание объекта выражения
        String expressionStr = "(3+(2*x))";
        Expression eParsed = Expression.parse(expressionStr);
        eParsed.print();
        System.out.println();

        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        // Печать выражения
        e.print();
        System.out.println();

        // Символьное дифференцирование
        Expression de = e.derivative("x");
        de.print();
        System.out.println();

        // Вычисление значения выражения при x = 10
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);
    }
}