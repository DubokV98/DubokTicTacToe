package com.company;

import java.util.Random;
import java.util.Scanner;

public class GameTicTacToe {

    public static final int USER_SIGN = 1; //X
    public static final int USER_SIGN_SECOND = 2; //O
    public static final int AI_SIGN = 2; //O
    public static int[][] gameField = new int[3][3];

    public static void main(String[] args) {
        mainMenuSelectionGameMode();
    }

    static void aiGame() {
        int countShot = 0;
        initField();
        while (true) {
            drawGameField();
            userShot(USER_SIGN);
            countShot++;
            if (countShot == 1) {
                int centralElement = gameField.length;
                if (gameField[centralElement / 2][centralElement / 2] != USER_SIGN) {
                    gameField[centralElement / 2][centralElement / 2] = AI_SIGN;
                } else {
                    gameField[0][0] = AI_SIGN;
                }
                countShot++;
            }
            else
                {
                if (countShot == 3) {
                    secondStepAI();
                    countShot++;
                }
                else
                    {
                    if (checkWin(USER_SIGN)) {
                        drawGameField();
                        System.out.println("USER X WIN!!!");
                        break;
                    }
                    if (countShot == Math.pow(gameField.length, 2)) {
                        drawGameField();
                        System.out.println("Draw!");
                        mainMenuSelectionGameMode();
                    }
                    aiShot();
                    countShot++;
                    if (checkWin(AI_SIGN)) {
                        drawGameField();
                        System.out.println("AI O WIN!!!");
                        mainMenuSelectionGameMode();
                    }
                }
            }
        }
    }

    static void modeTwoPlayersGame() {

        int countShot = 0;
        initField();
        while (true)
        {
            drawGameField();
            userShot(USER_SIGN);
            countShot++;
            if (checkWin(USER_SIGN))
            {
                drawGameField();
                System.out.println("USER X WIN!");
                mainMenuSelectionGameMode();
            }
            drawGameField();
            if (countShot == Math.pow(gameField.length, 2))
            {
                drawGameField();
                System.out.println("Draw!");
                mainMenuSelectionGameMode();
            }
            userShot(USER_SIGN_SECOND);
            countShot++;
            if (checkWin(USER_SIGN_SECOND))
            {
                drawGameField();
                System.out.println("USER O WIN!");
                mainMenuSelectionGameMode();
            }
        }
    }

    static void mainMenuSelectionGameMode() {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("Choose a game mode: ");
            System.out.println("1. Game against AI.");
            System.out.println("2. 2 player.");
            System.out.println("3. Exit.");
            try
            {
                choice = scanner.nextInt();
            }
            catch (Exception exception)
            {
                scanner.nextLine();
            }
            switch (choice)
            {
                case 1: {
                    aiGame();
                    break;
                }
                case 2: {
                    modeTwoPlayersGame();
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Invalid input!");
                }
            }
        }
    }

    static void drawGameField() {

        int sideDigits = 1;
        System.out.print(" ");
        for (int upperDigits = 1; upperDigits <= gameField.length; upperDigits++) {
            System.out.print("   " + upperDigits + "   ");
        }
        System.out.println("\r\n |------|------|------|");
        for (int i = 0; i < gameField.length; i++) {
            System.out.print(sideDigits + "|");
            for (int j = 0; j < gameField.length; j++) {
                if (gameField[i][j] == 0)
                {
                    System.out.print("      |");
                }
                if (gameField[i][j] == USER_SIGN)
                {
                    System.out.print("  X   |");
                }
                if (gameField[i][j] == USER_SIGN_SECOND)
                {
                    System.out.print("  O   |");
                }
            }
            System.out.println("\r\n |------|------|------|");
            sideDigits++;
        }
    }

    static void aiShot() {
        int stepX = -1;
        int stepY = -1;
        boolean ai_win = false;
        boolean user_win = false;
        // find win shot
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                if (!isCellBusy(i, j)) {
                    gameField[i][j] = AI_SIGN;
                    if (checkWin(AI_SIGN)) {
                        stepX = i;
                        stepY = j;
                        ai_win = true;
                    }
                    gameField[i][j] = 0;
                }
            }
        }
        //find user win shot
        if (!ai_win) {
            for (int i = 0; i < gameField.length; i++) {
                for (int j = 0; j < gameField.length; j++) {
                    if (!isCellBusy(i, j)) {
                        gameField[i][j] = USER_SIGN;
                        if (checkWin(USER_SIGN)) {
                            stepX = i;
                            stepY = j;
                            user_win = true;
                        }
                        gameField[i][j] = 0;
                    }
                }
            }
        }
        if (!ai_win && !user_win) {
            do {
                Random rnd = new Random();
                stepX = rnd.nextInt(gameField.length);
                stepY = rnd.nextInt(gameField.length);
            } while (isCellBusy(stepX, stepY));
        }
        gameField[stepX][stepY] = AI_SIGN;
    }

     static boolean isCellBusy(int x, int y) {

        if (x < 0 || y < 0 || x > gameField.length - 1 || y > gameField.length - 1)
        {
            return true;
        }
        return gameField[x][y] != 0;
    }

     static boolean checkLineOnWin(int start_x, int start_y, int dx, int dy, int sign) {

        for (int i = 0; i < gameField.length; i++) {
            if (gameField[start_x + i * dx][start_y + i * dy] != sign)
            {
                return false;
            }
        }
        return true;
    }

     static boolean checkWin(int sign) {

        for (int i = 0; i < gameField.length; i++) {
            // check lines
            if (checkLineOnWin(i, 0, 0, 1, sign))
            {
                return true;
            }
            // check columns
            if (checkLineOnWin(0, i, 1, 0, sign))
            {
                return true;
            }
        }
        // check diagonals
        if (checkLineOnWin(0, 0, 1, 1, sign))
        {
            return true;
        }
        if (checkLineOnWin(0, gameField.length - 1, 1, -1, sign))
        {
            return true;
        }
        return false;
    }

    static void initField() {

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = 0;
            }
        }
    }

    static void userShot(int sign) {

        int stepX = -1;
        int stepY = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            while (true) {
                try {
                    if (sign == 1) {
                        System.out.println("Go Х. Enter the coordinates x and y (1 - " + gameField.length + "): ");
                    } else {
                        System.out.println("Go О .Enter the coordinates x and y (1 - " + gameField.length + "): ");
                    }
                    stepX = scanner.nextInt() - 1;
                    stepY = scanner.nextInt() - 1;
                    break;
                } catch (Exception exception) {
                    System.out.println("Invalid input!");
                    scanner.nextLine();
                }
            }
        }
        while (isCellBusy(stepX, stepY));
        gameField[stepX][stepY] = sign;
    }

    static boolean secondStepAI()
    {
        int i=0;
        int j=0;

        while (i < gameField.length)
        {
            j=0;
            while (j < gameField.length)
            {
                if(gameField[i][j] == 0)
                {
                    gameField[i][j] = AI_SIGN;
                    return true;
                }
                j=j+2;
            }
            i=i+2;
        }
        return false;
    }
}
