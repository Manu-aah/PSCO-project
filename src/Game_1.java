import java.time.LocalDateTime;
import java.util.*;

public class Game_1 {
    private Player player;
    private Random random = new Random();

    public Game_1(Player player) {
        this.player = player;
    }

    public void playGame(Scanner scanner) {
        int totalScore = 0;

        System.out.println("\n---- DICE PATTERNS CHALLENGE ----");
        System.out.println("Enter dice positions to re-roll (example: 1 3 5)");
        System.out.println("Press Enter to keep all dice");

        System.out.println("\n=== ROLL YOUR DICE ===");
        int[] dice = rollDice();
        System.out.print("Dice: ");
        printDice(dice);

        int remainingRolls = 2;
        while (remainingRolls > 0) {
            System.out.print("\nWould you like to re-roll? (y/n): ");
            String choice = scanner.nextLine().trim();

            if (!choice.equalsIgnoreCase("y")) {
                System.out.println("Keeping all dice!");
                break;
            }

            System.out.print("Enter positions to re-roll (" + remainingRolls + " re-rolls left): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No positions entered, keeping all dice!");
                break;
            }

            String[] positions = input.split("\\s+");
            boolean rerolled = false;

            for (String posStr : positions) {
                try {
                    int pos = Integer.parseInt(posStr.trim());
                    if (pos >= 1 && pos <= 5) {
                        dice[pos - 1] = random.nextInt(6) + 1;
                        rerolled = true;
                    } else {
                        System.out.println("Invalid position: " + pos + " (must be 1-5)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: '" + posStr + "' — enter numbers 1-5");
                }
            }

            if (rerolled) {
                System.out.print("New dice: ");
                printDice(dice);
                remainingRolls--;
            }
        }

        totalScore = evaluateScore(dice);
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Final score: " + totalScore);

        player.addPoints(totalScore);
        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
        player.updateScores(0, totalScore);
    }

    private int[] rollDice() {
        int[] dice = new int[5];
        for (int i = 0; i < 5; i++) {
            dice[i] = random.nextInt(6) + 1;
        }
        return dice;
    }

    private void printDice(int[] dice) {
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i]);
            if (i < dice.length - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    private int evaluateScore(int[] dice) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int die : dice) {
            counts.put(die, counts.getOrDefault(die, 0) + 1);
        }

        boolean has3 = false;
        boolean has2 = false;
        int pairCount = 0;

        for (int count : counts.values()) {
            if (count == 5) return 50;
            if (count == 4) return 40;
            if (count == 3) has3 = true;
            if (count == 2) {
                has2 = true;
                pairCount++;
            }
        }

        if (has3 && has2) return 35;

        int[] sorted = dice.clone();
        Arrays.sort(sorted);
        if (Arrays.equals(sorted, new int[]{1, 2, 3, 4, 5}) ||
                Arrays.equals(sorted, new int[]{2, 3, 4, 5, 6})) {
            return 30;
        }

        if (has3) return 25;
        if (pairCount == 2) return 20;
        if (has2) return 10;
        return 0;
    }
}