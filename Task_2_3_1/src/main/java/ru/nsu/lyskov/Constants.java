package ru.nsu.lyskov;

/**
 * Класс, содержащий константы, используемые в приложении.
 */
public class Constants {
    // По условию лабораторной работы
    public static final int N_ROWS = 10;
    public static final int M_COLUMNS = 15;
    public static final int T_FOOD_ELEMENTS = 3;
    public static final int L_WIN_SCORE = N_ROWS * M_COLUMNS / 5;

    // Прочие параметры
    public static final int CELL_SIDE = 40;
    public static final long SPEED_BASE_INTERVAL = 500_000_000;
    public static final double SPEED_INCREASE_FACTOR = 0.95;
}