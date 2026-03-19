import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
public class Game_1 {
    private Player player;
    private Dice dice = new Dice();

    public Game_1(Player player) {
        this.player = player;
    }

    public void playGame(Scanner scanner) {
        System.out.println("\n---- DICE PATTERNS CHALLENGE ----");
        System.out.println("You will roll 5 dice and try to match the best possible pattern.");
        System.out.println("After the initial roll you can re-roll up to 2 times,");
        System.out.println("choosing exactly which dice to keep and which to re-roll.");
        System.out.println("Enter the positions of the dice you want to re-roll (e.g. 1 3 5).");
        System.out.println();
        System.out.println("Scoring:");
        System.out.println("  Five of a Kind  = 50 pts");
        System.out.println("  Four of a Kind  = 40 pts");
        System.out.println("  Full House      = 35 pts");
        System.out.println("  Straight (1-5 or 2-6) = 30 pts");
        System.out.println("  Three of a Kind = 25 pts");
        System.out.println("  Two Pairs       = 20 pts");
        System.out.println("  One Pair        = 10 pts");
        System.out.println("Good luck!");

        scanner.nextLine(); // clear leftover newline from menu's nextInt()

        System.out.println("\n=== ROLL YOUR DICE ===");
        int[] diceValues = rollDice();
        System.out.print("Dice: ");
        printDice(diceValues);

        int remainingRolls = 2;
        while (remainingRolls > 0) {
            System.out.print("\nWould you like to re-roll? (y/n): ");
            String choice = scanner.nextLine().trim();

            if (!choice.equalsIgnoreCase("y")) {
                System.out.println("Keeping all dice!");
                break;
            }

            System.out.print("Enter positions to re-roll (" + remainingRolls + " re-roll(s) left): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No positions entered, keeping all dice!");
                break;
            }

            // Boolean array to track which positions to re-roll (index 0 = die 1)
            boolean[] toReroll = new boolean[5];
            String[] parts = input.split("\\s+");
            boolean anyValid = false;

            for (String posStr : parts) {
                try {
                    int pos = Integer.parseInt(posStr.trim());
                    if (pos >= 1 && pos <= 5) {
                        toReroll[pos - 1] = true;
                        anyValid = true;
                    } else {
                        System.out.println("Invalid position: " + pos + " (must be 1-5)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: '" + posStr + "' — enter numbers 1-5");
                }
            }

            if (!anyValid) {
                System.out.println("No valid positions entered, re-roll not used.");
                continue;
            }

            for (int i = 0; i < 5; i++) {
                if (toReroll[i]) diceValues[i] = dice.rollDice(1);
            }

            remainingRolls--;
            System.out.print("New dice: ");
            printDice(diceValues);
            System.out.println("Re-rolls remaining: " + remainingRolls);
        }

        int totalScore = evaluateScore(diceValues);
        System.out.println("\n=== GAME OVER ===");
        System.out.print("Final dice: ");
        printDice(diceValues);
        System.out.println("Hand: " + evaluateHandName(diceValues));
        System.out.println("Score: " + totalScore);

        player.addPoints(totalScore);
        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
        player.updateScores(0, totalScore);
        System.out.println("Total points: " + player.getPoints());
    }

    private int[] rollDice() {
        int[] diceValues = new int[5];
        for (int i = 0; i < 5; i++) {
            diceValues[i] = dice.rollDice(1);
        }
        return diceValues;
    }

    private void printDice(int[] diceValues) {
        for (int i = 0; i < diceValues.length; i++) {
            System.out.print("[ " + diceValues[i] + " ]");
            if (i < diceValues.length - 1) System.out.print(" ");
        }
        System.out.println();
    }

    private int[] getFrequency(int[] diceValues) {
        int[] freq = new int[7]; // index 0 unused, 1-6 for dice faces
        for (int die : diceValues) freq[die]++;
        return freq;
    }

    private String evaluateHandName(int[] diceValues) {
        int score = classify(diceValues);
        if (score == 50) return "Five of a Kind";
        if (score == 40) return "Four of a Kind";
        if (score == 35) return "Full House";
        if (score == 30) return "Straight";
        if (score == 25) return "Three of a Kind";
        if (score == 20) return "Two Pairs";
        if (score == 10) return "One Pair";
        return "No match";
    }

    private int evaluateScore(int[] diceValues) {
        return classify(diceValues);
    }

    private int classify(int[] diceValues) {
        int[] freq = getFrequency(diceValues);
        boolean has3 = false;
        boolean has2 = false;
        int pairCount = 0;

        for (int i = 1; i <= 6; i++) {
            if (freq[i] == 5) return 50;
            if (freq[i] == 4) return 40;
            if (freq[i] == 3) has3 = true;
            if (freq[i] == 2) { has2 = true; pairCount++; }
        }

        if (has3 && has2) return 35;

        int[] sorted = diceValues.clone();
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