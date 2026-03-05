
import java.util.ArrayList;
import java.util.Scanner;
public class Game_2 {
    private final int[][] grid = new int[3][3];
    private final Scanner input = new Scanner(System.in);
    private final Dice dice;

    public Game_2() {
        this.dice = new Dice();
    }

    public void play() {
        System.out.println("==Dice Grid Puzzle==");
        System.out.println("Roll dices to fill the grid,");
        System.out.println("Scoring: 3 of a kind = 15pt");
        System.out.println("Straight = 12pt, Pair = 8pt, and All Different = 5");
        ;
        while (!isFull()) {
            printGrid();

            int roll = dice.rollDice(1);
            System.out.println("You rolled: " + roll);

            int row, col;
            while (true) {
                row = askInt("Row (0-2): ");
                col = askInt("Col (0-2): ");

                if (inBounds(row, col) && isEmpty(row, col)) {
                    place(row, col, roll);
                    break;
                } else {
                    System.out.println("Invalid or occupied cell. Try again.");

                }
            }
        }
    }

    private int askInt(String prompt) {
        System.out.print(prompt);
        while (!input.hasNextInt()) {
            input.next();
            System.out.print("Enter number: ");
        }
        return input.nextInt();
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 3 && c >= 0 && c < 3;
    }

    private boolean isEmpty(int r, int c) {
        return grid[r][c] == 0;
    }

    private void place(int r, int c, int value) {
        grid[r][c] = value;
    }

    private boolean isFull() {
        for (int[] row : grid) {
            for (int v : row) {
                if (v == 0) return false;
            }
        }
        return true;
    }

    private void printGrid() {
        System.out.println("\nCurrent Grid:");
        for (int r = 0; r < 3; r++) {
            System.out.print("| ");
            for (int c = 0; c < 3; c++) {
                System.out.print((grid[r][c] == 0 ? "." : grid[r][c]) + " ");
            }
            System.out.println("|");
        }
        System.out.println();
    }
}
