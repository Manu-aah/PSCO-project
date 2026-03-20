import java.time.LocalDateTime;
import java.util.Scanner;

public class Game_2 {
    private int[][] grid = new int[3][3];
    private Dice dice;
    private Player player;

    public Game_2(Player player) {
        this.player = player;
        this.dice = new Dice();
    }
    public void play(Scanner scanner) {
        System.out.println("---- Dice Grid Puzzle ----");
        System.out.println("Grid fills automatically row-wise.");
        System.out.println("Scoring:");
        System.out.println("3 of a kind = 15pts");
        System.out.println("Straight = 12pts");
        System.out.println("Pair = 8pts");
        System.out.println("All Different = 5pts");

        int row = 0;
        int col = 0;
        while (!isFull()) {
            printGrid();
            System.out.println("\nPress any number to roll the dice:");
            scanner.nextInt();
            int roll = dice.rollDice(1);
            System.out.println("You rolled: " + roll);
            grid[row][col] = roll;
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
        printGrid();
        int pointsBeforeGame = player.getPoints();
        calculateScore();
        int pointsEarnedThisRound = player.getPoints() - pointsBeforeGame;
        System.out.println("\nGame finished!");
        System.out.println("Points earned this round: " + pointsEarnedThisRound);
        System.out.println("Total points: " + player.getPoints());
        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
        player.updateScores(1, pointsEarnedThisRound);
    }
    private void calculateScore() {
        System.out.println("\nChecking rows...");
        for (int i = 0; i < 3; i++) {
            scoreLine(grid[i][0], grid[i][1], grid[i][2]);
        }
        System.out.println("\nChecking columns...");
        for (int j = 0; j < 3; j++) {
            scoreLine(grid[0][j], grid[1][j], grid[2][j]);
        }
    }
    private void scoreLine(int a, int b, int c) {
        int[] freq = new int[7];
        freq[a]++;
        freq[b]++;
        freq[c]++;

        if (freq[a] == 3) {
            System.out.println("Three of a Kind! +15");
            player.addPoints(15);

        } else {
            int min = Math.min(a, Math.min(b, c));
            int max = Math.max(a, Math.max(b, c));

            if (max - min == 2 && freq[min] == 1 && freq[min + 1] == 1 && freq[min + 2] == 1) {
                System.out.println("Straight! +12");
                player.addPoints(12);

            } else if (freq[a] == 2 || freq[b] == 2 || freq[c] == 2) {

                System.out.println("Pair! +8");
                player.addPoints(8);

            } else {
                System.out.println("All Different! +5");
                player.addPoints(5);
            }
        }
    }

    private boolean isFull() {

        for (int[] row : grid) {
            for (int v : row) {
                if (v == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    private void printGrid() {

        System.out.println("\nCurrent Grid:");

        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|");
        }
    }
}

