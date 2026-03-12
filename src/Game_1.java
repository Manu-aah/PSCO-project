import java.util.*;
public class Game_1 {

    public class SimpleDiceGame {
        private static final Scanner scanner = new Scanner(System.in);
        private static final Random random = new Random();

        public static void main(String[] args) {
            int totalScore = 0;
            int roundNumber = 1;

            System.out.println("\n=== DICE PATTERNS CHALLENGE ===");
            System.out.println("Enter dice positions to re-roll (e.g., '1 3 5')");
            System.out.println("Press Enter to keep all dice\n");

            boolean playing = true;
            while (playing) {
                System.out.println("\n=== ROUND " + roundNumber + " ===");

                int[] dice = rollDice();
                System.out.print("Dice: ");
                printDice(dice);

                int remainingRolls = 2;

                while (remainingRolls > 0) {
                    System.out.print("Re-roll (" + remainingRolls + " left): ");
                    String input = scanner.nextLine().trim();

                    if (input.isEmpty()) {
                        System.out.println("Keeping all dice!");
                        break;
                    }

                    String[] positions = input.split(" ");
                    for (String posStr : positions) {
                        try {
                            int pos = Integer.parseInt(posStr);
                            if (pos >= 1 && pos <= 5) {
                                dice[pos-1] = random.nextInt(6) + 1;
                            }
                        } catch (NumberFormatException ignored) {}
                    }

                    System.out.print("New dice: ");
                    printDice(dice);
                    remainingRolls--;
                }

                int score = evaluateScore(dice);
                totalScore += score;

                System.out.println("Round score: " + score);
                System.out.println("Total score: " + totalScore);

                System.out.print("\nPlay again? (y/n): ");
                playing = scanner.nextLine().trim().equalsIgnoreCase("y");
                roundNumber++;
            }

            System.out.println("\n=== GAME OVER ===");
            System.out.println("Final score: " + totalScore);
        }

        private static int[] rollDice() {
            int[] dice = new int[5];
            for (int i = 0; i < 5; i++) {
                dice[i] = random.nextInt(6) + 1;
            }
            Arrays.sort(dice);
            return dice;
        }

        private static void printDice(int[] dice) {
            for (int die : dice) {
                System.out.print(die + " ");
            }
            System.out.println();
        }

        private static int evaluateScore(int[] dice) {
            Map<Integer, Integer> counts = new HashMap<>();
            for (int die : dice) {
                counts.put(die, counts.getOrDefault(die, 0) + 1);
            }

            boolean has3 = false, has2 = false;
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
            Arrays.sort(dice);
            boolean straight1 = Arrays.equals(dice, new int[]{1,2,3,4,5});
            boolean straight2 = Arrays.equals(dice, new int[]{2,3,4,5,6});
            if (straight1 || straight2) return 30;

            if (has3) return 25;
            if (pairCount == 2) return 20;
            if (has2) return 10;

            return 0;
        }
    }


}
