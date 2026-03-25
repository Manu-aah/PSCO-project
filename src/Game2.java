import java.time.LocalDateTime;
import java.util.Scanner;

public class Game2 {
    private int[][] grid = new int[3][3];
    private Dice dice;
    private Player player;

    public Game2(Player player) {
        this.player = player;
        this.dice = new Dice();
    }

    // fills the grid in one roll, displays it, then calculates and shows the score
    public void play(Scanner scanner) {
        System.out.println("---- Dice Grid Puzzle ----");
        System.out.println("Press Enter to fill the entire grid at once.");
        System.out.println("Scoring per row and column:");
        System.out.println("  3 of a kind  = 15pts");
        System.out.println("  Straight     = 12pts");
        System.out.println("  Pair         = 8pts");
        System.out.println("  All Different= 5pts");

        System.out.println("\nPress Enter to roll:");
        scanner.nextLine(); // clears the nextInt scanner buffer
        scanner.nextLine(); // waits for the player to press Enter

        // Fills the entire grid in one go
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid[row][col] = dice.rollDice(1);
            }
        }

        System.out.println("\nYour grid:");
        dice.printDiceGrid(grid);

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

    // Iterates over all 3 rows and 3 columns and scores each line
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

    // Scores a single line of 3 dice values then checks for three of a kind, straight, pair or all different
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
}