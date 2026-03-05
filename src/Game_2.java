
import java.util.ArrayList;
import java.util.Scanner;
public class Game_2 {
    private final int[][] grid = new int[3][3];
    private final Dice dice;
    private int inp;

    public Game_2() {
        this.dice = new Dice();
    }

    public void play(Scanner scanner) {
        System.out.println("==Dice Grid Puzzle==");
        System.out.println("Roll dices to fill the grid,");
        System.out.println("Scoring: 3 of a kind = 15pt, Straight = 12pt,\nPair = 8pt, and All Different = 5");

        while (!isFull()) {
            printGrid();
            int roll = 0;
            System.out.println("Roll dices to fill the grid, Press any integer to roll:");
            while(!isFull()){
                while(scanner.hasNextInt()){

                roll = dice.rollDice(1);
                System.out.println("You rolled: " + roll);
                }
            }
        }
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
        System.out.println("\nCurrent Grid:"); //i is for rows and c is for columns
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|\n");
        }
    }
}
