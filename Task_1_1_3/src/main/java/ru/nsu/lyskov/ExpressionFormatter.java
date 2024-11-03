package ru.nsu.lyskov;

import java.util.Stack;

/**
 * Класс для форматирования арифметических выражений, в котором добавляются недостающие скобки для
 * явного указания порядка операций. Это необходимо для правильного выполнения математических
 * операций с учетом приоритетов.
 */
public class ExpressionFormatter {

    /**
     * Форматирует выражение, добавляя необходимые скобки для всех операторов с учетом их
     * приоритета. Удаляет все пробелы из строки, затем преобразует её в формат, в котором порядок
     * операций становится однозначным.
     *
     * @param expression арифметическое выражение в виде строки, которое нужно отформатировать
     * @return отформатированная строка с расставленными скобками
     */
    public static String formatExpression(String expression) {
        String cleanExpr = expression.replaceAll("\\s+", "");

        Stack<String> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder currentOperand = new StringBuilder();

        for (int i = 0; i < cleanExpr.length(); i++) {
            char ch = cleanExpr.charAt(i);

            if (Character.isLetterOrDigit(ch) || ch == '.') {
                currentOperand.append(ch);
            } else if (isOperator(ch)) {
                if (!currentOperand.isEmpty()) {
                    operands.push(currentOperand.toString());
                    currentOperand.setLength(0);
                }

                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    processTopOperator(operands, operators.pop());
                }

                operators.push(ch);
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                if (!currentOperand.isEmpty()) {
                    operands.push(currentOperand.toString());
                    currentOperand.setLength(0);
                }

                while (!operators.isEmpty() && operators.peek() != '(') {
                    processTopOperator(operands, operators.pop());
                }
                operators.pop();
            }
        }

        if (!currentOperand.isEmpty()) {
            operands.push(currentOperand.toString());
        }

        while (!operators.isEmpty()) {
            processTopOperator(operands, operators.pop());
        }

        return operands.pop();
    }

    /**
     * Определяет, является ли символ арифметическим оператором.
     *
     * @param ch символ для проверки
     * @return true, если символ является одним из операторов: '+', '-', '*', или '/', иначе false
     */
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * Определяет приоритет для заданного оператора. Операторы умножения и деления имеют более
     * высокий приоритет (возвращает 2) по сравнению с операторами сложения и вычитания (возвращает
     * 1).
     *
     * @param operator оператор, приоритет которого нужно определить
     * @return целочисленное значение, отражающее приоритет оператора
     */
    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return 0;
    }

    /**
     * Выполняет операцию над двумя верхними операндами в стеке, используя указанный оператор.
     * Результат операции сохраняется в стеке операндов. Метод добавляет скобки к результату
     * операции.
     *
     * @param operands стек операндов, содержащий значения для выполнения операции
     * @param operator оператор для выполнения операции над операндами
     */
    private static void processTopOperator(Stack<String> operands, char operator) {
        String right = operands.pop();
        String left = operands.pop();
        operands.push("(" + left + operator + right + ")");
    }

}
