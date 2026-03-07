
import java.util.ArrayList;
import java.util.Scanner;
public class Game_2 {
    private final int[][] grid = new int[3][3];
    private final Dice dice;
    private int inp;
    Player player;

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
            while (!isFull()) {
                while (scanner.hasNextInt()) {
                    roll = dice.rollDice(1);
                    System.out.println("You rolled: " + roll);
                }}}}

        private int scoreLine(int a, int b, int c) {

            int[] freq = new int[7];
            freq[a]++;
            freq[b]++;
            freq[c]++;

            int pattern;

            // three of a kind
            if (freq[a] == 3 || freq[b] == 3 || freq[c] == 3) {
                pattern = 1;
            } else {
                // straight
                int min = Math.min(a, Math.min(b, c));
                int max = Math.max(a, Math.max(b, c));
                if (max - min == 2 &&
                        freq[min] == 1 &&
                        freq[min + 1] == 1 &&
                        freq[min + 2] == 1) {
                    pattern = 2;
                } else if (freq[a] == 2 || freq[b] == 2 || freq[c] == 2) {
                    pattern = 3; // pair
                } else {
                    pattern = 4; // all different
                }
            }

            switch (pattern) {
                case 1:
                    System.out.println("Three of a Kind! (15pts)");
                    player.addPoints(15);
                    break;
                case 2:
                    System.out.println("Straight! (12pts) ");
                    player.addPoints(12);
                    break;
                case 3:
                    System.out.println("Pair! (8pts)");
                    player.addPoints(8);
                    break;
                case 4:
                    System.out.println("All Different! (5pts)");
                    player.addPoints(5);
                    break;
                default:
                    System.out.println("No pattern match");
            }
            System.out.println(player.getPoints());
            return pattern;
        }

    public boolean isFull() {
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
        }}}