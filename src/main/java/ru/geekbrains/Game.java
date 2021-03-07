package ru.geekbrains;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private static final int SIZE = 5;
    private static final int WIN_LENGHT = 4;
    private static final String PATTERNTEXT = "(^[a-eA-E]{1})([1-5]{1}$)";

    private static final char EMPTY_CELL = '.';
    private static final char KRESTIK_CELL = 'X';
    private static final char NOLIK_CELL = 'O';

    private static char[][] field = new char[SIZE][SIZE];




    public static void main(String[] args) {
        initField();
        printField();
        humanTurn();
        printField();
        humanTurn();
        printField();

    }



    //Инициализация поля
    private static void initField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = EMPTY_CELL;
            }

        }
    }


    //Вывод поля на экран
    private static void printField() {
        System.out.print(" ");
        for (int i = 0; i < field.length; i++) {
            System.out.print("  "+ (char)(i+65));
        }

        System.out.println();

        for (int i = 0; i < field.length; i++) {
            System.out.print(i+1);
            for (int j = 0; j < field.length; j++) {
                System.out.print("  " + field[i][j]);
            }
            System.out.println();
        }
    }


    //Ход человека
    public static void humanTurn () {
        Scanner sc = new Scanner(System.in);
        String turn = sc.nextLine();

        Pattern pattern = Pattern.compile(PATTERNTEXT);
        Matcher matcher = pattern.matcher(turn);
        while (!matcher.find()) {
            System.out.println("Введен неправильный формат! \nВведите поле еще раз!");
            turn = sc.nextLine();
            matcher = pattern.matcher(turn);
        }

        char column = matcher.group(1).charAt(0);
        int string = Integer.parseInt(matcher.group(2));

        Cell cell =  new Cell(column,string);

        setCell(cell, Players.HUMAN);

    }





    //Ход компьютера
    public static void computerTurn () {
        Combination maxCombination = getMaxCombination();
        Cell start = parseCell(maxCombination.start);
        Cell end = parseCell(maxCombination.end);
        Cell turn;

        switch (maxCombination.combType) {
            case HORIZONTAL:
                setCell(turn, Players.COMPUTER );
                break;

            case VERTICAL:
                setCell(turn, Players.COMPUTER );
                break;

            case LEFTTORIGHTDIAGONAL:
                setCell(turn, Players.COMPUTER );
                break;

            case RIGHTTOLEFTDIAGONAL:
                setCell(turn, Players.COMPUTER );
                break;
        }

    }




    //Определение лучшей комбинации игрока на поле
    private static Combination getMaxCombination() {
        CombType combType = CombType.NOCOMB;
        int maxHumanCombLenght = 1;
        String maxHumanCombStart = "";
        String maxHumanCombEnd = "";
        int currentHumanCombLenght = 1;
        String currentHumanCombStart;
        String currentHumanCombEnd;
        boolean isCellsSame;
        int m, n;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                currentHumanCombStart = i + "" + j;  //координаты начала текущей последовательности
                m = i;
                n = j;
                currentHumanCombLenght = 1;

                if (field[i][j] == KRESTIK_CELL) {


                    //Проверка по горизонтали вправо
                    if (j > field.length - maxHumanCombLenght) {
                        do {
                            if (field[m][n] == field[m][n + 1]) {
                                isCellsSame = true;
                                currentHumanCombEnd = m + "" + n;  //координаты конца текущей последовательнсти

                                currentHumanCombLenght++;
                                if (currentHumanCombLenght > maxHumanCombLenght) {
                                    maxHumanCombLenght = currentHumanCombLenght;
                                    maxHumanCombStart = currentHumanCombStart;
                                    maxHumanCombEnd = currentHumanCombEnd;
                                }

                                n++;
                                combType = CombType.HORIZONTAL;

                            } else {
                                isCellsSame = false;
                            }
                        } while (isCellsSame);
                    }

                    //Проверка по вертикали вниз
                    if (i > field.length - maxHumanCombLenght) {
                        do {
                            if (field[m][n] == field[m + 1][n]) {
                                isCellsSame = true;
                                currentHumanCombEnd = m + "" + n;  //координаты конца текущей последовательнсти

                                currentHumanCombLenght++;
                                if (currentHumanCombLenght > maxHumanCombLenght) {
                                    maxHumanCombLenght = currentHumanCombLenght;
                                    maxHumanCombStart = currentHumanCombStart;
                                    maxHumanCombEnd = currentHumanCombEnd;
                                }

                                m++;
                                combType = CombType.VERTICAL;

                            } else {
                                isCellsSame = false;
                            }
                        } while (isCellsSame);
                    }

                    //Проверка по диагонали вправо вниз
                    if ((i > field.length - maxHumanCombLenght) && (j > field.length - maxHumanCombLenght)) {
                        do {
                            if (field[m][n] == field[m + 1][n + 1]) {
                                isCellsSame = true;
                                currentHumanCombEnd = m + "" + n;  //координаты конца текущей последовательнсти

                                currentHumanCombLenght++;
                                if (currentHumanCombLenght > maxHumanCombLenght) {
                                    maxHumanCombLenght = currentHumanCombLenght;
                                    maxHumanCombStart = currentHumanCombStart;
                                    maxHumanCombEnd = currentHumanCombEnd;
                                }

                                m++;
                                n++;
                                combType = CombType.LEFTTORIGHTDIAGONAL;

                            } else {
                                isCellsSame = false;
                            }
                        } while (isCellsSame);
                    }

                    //Проверка по диагонали влево вниз
                    if ((i > field.length - maxHumanCombLenght) && ((maxHumanCombLenght - j) > field.length - maxHumanCombLenght)) {
                        do {
                            if (field[m][n] == field[m + 1][n - 1]) {
                                isCellsSame = true;
                                currentHumanCombEnd = m + "" + n;  //координаты конца текущей последовательнсти

                                currentHumanCombLenght++;
                                if (currentHumanCombLenght > maxHumanCombLenght) {
                                    maxHumanCombLenght = currentHumanCombLenght;
                                    maxHumanCombStart = currentHumanCombStart;
                                    maxHumanCombEnd = currentHumanCombEnd;
                                }

                                m++;
                                n--;
                                combType = CombType.RIGHTTOLEFTDIAGONAL;

                            } else {
                                isCellsSame = false;
                            }
                        } while (isCellsSame);
                    }

                }
            }
        }

        return new Combination(maxHumanCombStart, maxHumanCombEnd, combType);
    }


    //Установка значения ячейки
    public static void setCell (Cell cell, Players player) {
        switch (player) {
            case HUMAN :
                field[cell.string][(cell.column] = KRESTIK_CELL; //65 - номер символа 'A'
                break;

            case COMPUTER :
                field[cell.string][(cell.column] = NOLIK_CELL; //65 - номер символа 'A'
                break;
            }
        }

    }


