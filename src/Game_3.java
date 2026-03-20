import java.time.LocalDateTime;
import java.util.Scanner;

public class Game_3 {

    private Player player;
    private Dice dice = new Dice();
    private final int MAX_ATTEMPTS = 10;

    public Game_3(Player player) {
        this.player = player;
    }

    public void playGame(Scanner scanner) {

        int[] secretCode = new int[4];

        for (int i = 0; i < 4; i++) {
            secretCode[i] = dice.rollDice(1);
        }
        System.out.println("\n---- DICE CODEBREAKER ----");
        System.out.println("Guess the 4 dice numbers (1-6)");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts.");

        boolean guessed = false;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            int[] guess = new int[4];

            System.out.println("\nAttempt " + attempt);
            System.out.print("Enter 4 numbers separated by spaces: ");

            for (int i = 0; i < 4; i++) {

                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Enter numbers 1-6.");
                    scanner.next();
                }

                guess[i] = scanner.nextInt();

                if (guess[i] < 1 || guess[i] > 6) {
                    System.out.println("Numbers must be between 1 and 6.");
                    i--;
                }
            }

            scanner.nextLine(); // clear newline

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

            System.out.println("Correct number & position: " + correctPosition);
            System.out.println("Correct number but wrong position: " + correctNumberWrongPlace);

            if (correctPosition == 4) {

                System.out.println("You cracked the code!");

                int score = (MAX_ATTEMPTS - attempt + 1) * 5;
                System.out.println("Score earned: " + score);

                player.addPoints(score);
                player.updateScores(2, score);

                guessed = true;
                break;
            }
        }

        if (!guessed) {
            System.out.print("You failed! The code was: ");

            for (int num : secretCode) {
                System.out.print(num + " ");
            }

            System.out.println();
        }

        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
    }
}