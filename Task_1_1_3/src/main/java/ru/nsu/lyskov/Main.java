package ru.nsu.lyskov;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        // Печать выражения
        e.print();  // Выведет: (3+(2*x))
        System.out.println();

        // Символьное дифференцирование
        Expression de = e.derivative("x");
        de.print();  // Выведет: (0+((0*x)+(2*1)))
        System.out.println();

        // Вычисление значения выражения при x = 10
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);  // Выведет: 23
    }
}