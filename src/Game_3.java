import java.util.Scanner;
import java.util.Random;

public class Game_3 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        int[] secretCode = new int[4];
        int[] guess = new int[4];

        // Generate random code (numbers 1-6)
        for (int i = 0; i < 4; i++) {
            secretCode[i] = rand.nextInt(6) + 1;
        }

        System.out.println("Welcome to Dice Codebreaker!");
        System.out.println("Guess the 4 dice numbers (1-6)");

        // Player enters guess
        System.out.println("Enter 4 numbers:");

        for (int i = 0; i < 4; i++) {
            guess[i] = input.nextInt();
        }

        int correctPosition = 0;

        // Check correct number in correct position
        for (int i = 0; i < 4; i++) {
            if (guess[i] == secretCode[i]) {
                correctPosition++;
            }
        }

        // Show result
        System.out.println("Numbers correct and in correct position: " + correctPosition);

        input.close();
    }
}