package ru.nsu.lyskov;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class Expression {
    public abstract void print();

    public abstract Expression derivative(String variable);

    abstract double eval(Map<String, Double> variables);

    public double eval(String variablesStr) {
        Map<String, Double> variables = parseVariables(variablesStr);
        return eval(variables);
    }

    private Map<String, Double> parseVariables(String variablesStr) {
        Map<String, Double> variables = new HashMap<>();
        String[] assignments = variablesStr.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String variable = parts[0].trim();
                double value = Double.parseDouble(parts[1].trim());
                variables.put(variable, value);
            } else {
                throw new IllegalArgumentException("Invalid format: " + assignment);
            }
        }
        return variables;
    }

    public static Expression parse(String input) {
        input = input.replaceAll("\\s", "");
        return parseExpression(input);
    }

    private static Expression parseExpression(String input) {
        if (input.matches("\\d+(.\\d+)?")) {
            return new Number(Double.parseDouble(input));
        }
        if (input.matches("[a-zA-Z]+")) {
            return new Variable(input);
        }

        if (input.startsWith("(") && input.endsWith(")")) {
            return parseComplexExpression(input.substring(1, input.length() - 1));
        }

        throw new IllegalArgumentException("Invalid format: " + input);
    }

    private static Expression parseComplexExpression(String input) {
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
            throw new IllegalArgumentException("Cannot parse expression: " + input);
        }

        // Разделение выражения на две части по основной операции
        String leftExpr = input.substring(0, mainOpPos);
        String rightExpr = input.substring(mainOpPos + 1);

        // Создание соответствующего выражения
        Expression left = parseExpression(leftExpr);
        Expression right = parseExpression(rightExpr);

        return switch (mainOp) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException("Unknown operation: " + mainOp);
        };
    }
}
