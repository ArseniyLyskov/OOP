package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;

/**
 * Класс для представления переменной в математическом выражении.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Конструктор, который создаёт переменную с указанным именем.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Печатает имя переменной.
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    @Override
    public void print(PrintStream out) {
        out.print(name);
    }

    /**
     * Возвращает производную переменной по указанной переменной. Если текущая переменная совпадает
     * с указанной, производная равна 1, иначе 0.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную (1 или 0)
     */
    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Упрощает текущее выражение, создавая новое (упрощённое) выражение. Для {@link Variable} -
     * упрощение не нужно.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Вычисляет значение переменной на основе карты переменных.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     * @return значение переменной
     * @throws IncorrectAssignmentException если переменная не была определена
     */
    @Override
    public double eval(Map<String, Double> variables) throws IncorrectAssignmentException {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new IncorrectAssignmentException("Variable " + name + " is not defined");
    }

    /**
     * Сравнивает два объекта на равенство.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable variable = (Variable) obj;
        return Objects.equals(name, variable.name);
    }
}
