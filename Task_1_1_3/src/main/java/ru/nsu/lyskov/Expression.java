package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import ru.nsu.lyskov.exceptions.DivisionByZeroException;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;
import ru.nsu.lyskov.exceptions.IncorrectExpressionException;

/**
 * Абстрактный класс для математических выражений. Определяет интерфейс для операций печати,
 * дифференцирования и вычисления выражений.
 */
public abstract class Expression {

    /**
     * Печатает выражение в указанный поток вывода.
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    public abstract void print(PrintStream out);

    /**
     * Возвращает производную выражения по указанной переменной.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную
     */
    public abstract Expression derivative(String variable);

    /**
     * Упрощает текущее выражение, создавая новое (упрощённое) выражение.
     *
     * @return упрощённое выражение
     */
    public abstract Expression simplify() throws DivisionByZeroException;

    /**
     * Вычисляет значение выражения на основе карты переменных.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     * @return результат вычисления выражения
     * @throws DivisionByZeroException      если в процессе вычисления произошло деление на ноль
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменных
     */
    abstract double eval(Map<String, Double> variables)
            throws DivisionByZeroException, IncorrectAssignmentException;

    /**
     * Вычисляет значение выражения на основе строки с присвоениями переменных.
     *
     * @param variablesStr строка с присвоениями переменных в формате "переменная = значение; ..."
     * @return результат вычисления выражения
     * @throws DivisionByZeroException      если в процессе вычисления произошло деление на ноль
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменных
     */
    public double eval(String variablesStr)
            throws DivisionByZeroException, IncorrectAssignmentException {
        Map<String, Double> variables = parseVariables(variablesStr);
        return eval(variables);
    }

    /**
     * Разбирает строку с присвоениями переменных и возвращает карту значений переменных.
     *
     * @param variablesStr строка с присвоениями переменных в формате "переменная = значение; ..."
     * @return карта, содержащая имена переменных и их значения
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменных
     */
    private Map<String, Double> parseVariables(String variablesStr)
            throws IncorrectAssignmentException {
        Map<String, Double> variables = new HashMap<>();
        if (variablesStr.isEmpty()) {
            return variables;
        }
        String[] assignments = variablesStr.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String variable = parts[0].trim();
                double value = Double.parseDouble(parts[1].trim());
                variables.put(variable, value);
            } else {
                throw new IncorrectAssignmentException(
                        "Invalid assignment of variables: " + assignment
                );
            }
        }
        return variables;
    }

    /**
     * Разбирает строку с выражением и создает соответствующий объект Expression.
     *
     * @param input строка с выражением
     * @return объект Expression, представляющий выражение
     * @throws IncorrectExpressionException если формат выражения некорректен
     */
    public static Expression parse(String input) throws IncorrectExpressionException {
        String formattedExpression;
        try {
            formattedExpression = ExpressionFormatter.formatExpression(input);
        } catch (RuntimeException e) {
            throw new IncorrectExpressionException("Cannot parse expression: " + input);
        }
        return parseExpression(formattedExpression);
    }

    /**
     * Разбирает простое выражение (число или переменная) или вызывает разбор сложного выражения.
     *
     * @param input строка с выражением
     * @return объект Expression, представляющий выражение
     * @throws IncorrectExpressionException если формат выражения некорректен
     */
    private static Expression parseExpression(String input) throws IncorrectExpressionException {
        // Если это число, возвращаем объект Number
        if (input.matches("\\d+(\\.\\d+)?")) {
            return new Number(Double.parseDouble(input));
        }
        // Если это переменная, возвращаем объект Variable
        if (input.matches("[a-zA-Z]+")) {
            return new Variable(input);
        }

        // Если это выражение в скобках
        if (input.startsWith("(") && input.endsWith(")")) {
            return parseComplexExpression(input.substring(1, input.length() - 1));
        }

        throw new IncorrectExpressionException("Invalid format: " + input);
    }

    /**
     * Разбирает сложное выражение в скобках и создает объект соответствующего выражения.
     *
     * @param input строка с выражением
     * @return объект Expression, представляющий выражение
     * @throws IncorrectExpressionException если формат выражения некорректен
     */
    private static Expression parseComplexExpression(String input)
            throws IncorrectExpressionException {
        Stack<Character> brackets = new Stack<>();
        int mainOpPos = -1;
        char mainOp = 0;

        // Поиск основной операции вне вложенных скобок
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                brackets.push(c);
            } else if (c == ')') {
                brackets.pop();
            } else if (brackets.isEmpty()) {
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    mainOp = c;
                    mainOpPos = i;
                    break;
                }
            }
        }

        if (mainOpPos == -1) {
            throw new IncorrectExpressionException("Cannot parse expression: " + input);
        }

        // Разделение выражения на две части по основной операции
        String leftExpr = input.substring(0, mainOpPos);
        String rightExpr = input.substring(mainOpPos + 1);

        // Создание соответствующего выражения
        Expression left = parseExpression(leftExpr);
        Expression right = parseExpression(rightExpr);

        switch (mainOp) {
            case '+':
                return new Add(left, right);
            case '-':
                return new Sub(left, right);
            case '*':
                return new Mul(left, right);
            case '/':
                return new Div(left, right);
            default:
                throw new IncorrectExpressionException("Unknown operation: " + mainOp);
        }
    }

    /**
     * Сравнивает два объекта на равенство.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    @Override
    public abstract boolean equals(Object obj);
}