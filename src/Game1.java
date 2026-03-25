import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class Game1 {
    private Player player;
    private Dice dice = new Dice();

    public Game1(Player player) {
        this.player = player;
    }

    // This is the Main game loop, it shows instructions, handles rolling and re-rolling, then scores the final hand
    public void playGame(Scanner scanner) {
        System.out.println("\n---- DICE PATTERNS CHALLENGE ----");
        System.out.println("You will roll 5 dice and try to match the best possible pattern.");
        System.out.println("After the initial roll you can re-roll up to 2 times,");
        System.out.println("choosing exactly which dice to keep and which to re-roll.");
        System.out.println("Enter the positions of the dice you want to re-roll (e.g. 1 3 5).");
        System.out.println();
        System.out.println("Scoring:");
        System.out.println("  Five of a Kind       = 50 pts");
        System.out.println("  Four of a Kind       = 40 pts");
        System.out.println("  Full House           = 35 pts");
        System.out.println("  Straight (1-5 or 2-6)= 30 pts");
        System.out.println("  Three of a Kind      = 25 pts");
        System.out.println("  Two Pairs            = 20 pts");
        System.out.println("  One Pair             = 10 pts");
        System.out.println("Good luck!");

        scanner.nextLine(); // clear leftover newline from menu's nextInt()

        System.out.println("\n=== ROLL YOUR DICE ===");
        int[] diceValues = rollDice();
        System.out.println("  1       2       3       4       5  ");
        dice.printDice(diceValues);

        int remainingRolls = 2;
        while (remainingRolls > 0) {
            String choice;
            while (true) {
                System.out.print("\nWould you like to re-roll? (y/n): ");
                choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("y") || choice.equals("n")) break;
                System.out.println("Invalid input. Please enter y or n.");
            }

            if (choice.equals("n")) {
                System.out.println("Keeping all dice!");
                break;
            }

            System.out.print("Enter positions to re-roll (" + remainingRolls + " re-roll(s) left): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No positions entered, keeping all dice!");
                break;
            }

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
            System.out.println("New dice:");
            System.out.println("  1       2       3       4       5  ");
            dice.printDice(diceValues);
            System.out.println("Re-rolls remaining: " + remainingRolls);
        }

        int totalScore = evaluateScore(diceValues);
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Final dice:");
        System.out.println("  1       2       3       4       5  ");
        dice.printDice(diceValues);
        System.out.println("Hand: " + evaluateHandName(diceValues));
        System.out.println("Score: " + totalScore);

        player.addPoints(totalScore);
        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
        player.updateScores(0, totalScore);
        System.out.println("Total points: " + player.getPoints());
    }

    // Rolls 5 dice and returns their values as an array
    private int[] rollDice() {
        int[] diceValues = new int[5];
        for (int i = 0; i < 5; i++) {
            diceValues[i] = dice.rollDice(1);
        }
        return diceValues;
    }

    // Returns a frequency array where index = face value (1-6) and value = how many times it appears
    private int[] getFrequency(int[] diceValues) {
        int[] freq = new int[7];
        for (int die : diceValues) freq[die]++;
        return freq;
    }

    // Returns the name of the hand based on the score
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

    // Returns the score for the current dice values
    private int evaluateScore(int[] diceValues) {
        return classify(diceValues);
    }

    // Checks the dice against every pattern from highest to lowest and returns the matching score
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