import java.time.LocalDateTime;
import java.util.Scanner;

public class Game3 {

    private Player player;
    private Dice dice = new Dice();
    private final int MAX_ATTEMPTS = 10;

    public Game3(Player player) {
        this.player = player;
    }

    //generates a secret code, handles guesses and feedback, scores on success
    public void playGame(Scanner scanner) {

        int[] secretCode = new int[4];
        for (int i = 0; i < 4; i++) {
            secretCode[i] = dice.rollDice(1);
        }

        System.out.println("\n---- DICE CODEBREAKER ----");
        System.out.println("Guess the 4 dice numbers (1-6)");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts.");

        scanner.nextLine(); // clear the scanner

        boolean guessed = false;
        boolean quit = false;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            int[] guess = new int[4];

            System.out.println("\nAttempt " + attempt);

            // Keeps prompting until the player enters exactly 4 valid numbers or types 0 to quit
            while (true) {
                System.out.print("Enter exactly 4 numbers (1-6) separated by spaces (or 0 to quit): ");
                String line = scanner.nextLine().trim();

                if (line.equals("0")) {
                    System.out.println("Quitting game...");
                    quit = true;
                    break;
                }

                String[] parts = line.split(" ");

                if (parts.length != 4) {
                    System.out.println("You must enter exactly 4 numbers.");
                    continue;
                }

                boolean valid = true;
                for (int i = 0; i < 4; i++) {
                    try {
                        guess[i] = Integer.parseInt(parts[i]);
                        if (guess[i] < 1 || guess[i] > 6) {
                            System.out.println("Each number must be between 1 and 6.");
                            valid = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: '" + parts[i] + "' — enter numbers only.");
                        valid = false;
                        break;
                    }
                }

                if (valid) break;
            }

            if (quit) break;

            // Show the guess as dice
            System.out.println("Your guess:");
            dice.printDice(guess);

            // Checks how many digits are correct in the right position
            // and how many are correct but in the wrong position
            int correctPosition = 0;
            int correctNumberWrongPlace = 0;

            boolean[] secretUsed = new boolean[4];
            boolean[] guessUsed = new boolean[4];

            for (int i = 0; i < 4; i++) {
                if (guess[i] == secretCode[i]) {
                    correctPosition++;
                    secretUsed[i] = true;
                    guessUsed[i] = true;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (guessUsed[i]) continue;
                for (int j = 0; j < 4; j++) {
                    if (!secretUsed[j] && guess[i] == secretCode[j]) {
                        correctNumberWrongPlace++;
                        secretUsed[j] = true;
                        break;
                    }
                }
            }

            System.out.println("Correct number & position:         " + correctPosition);
            System.out.println("Correct number but wrong position: " + correctNumberWrongPlace);

            // Player wins if all 4 digits are in the correct position
            if (correctPosition == 4) {
                System.out.println("\nYou cracked the code!");
                int score = (MAX_ATTEMPTS - attempt + 1) * 5;
                System.out.println("Score earned: " + score);
                player.addPoints(score);
                player.updateScores(2, score);
                guessed = true;
                break;
            }
        }

        // Only shows the failed message if the player ran out of attempts, not if they quit
        if (!guessed && !quit) {
            System.out.println("\nYou failed! The code was:");
            dice.printDice(secretCode);
            player.updateScores(2, 0);
        }

        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
    }
}