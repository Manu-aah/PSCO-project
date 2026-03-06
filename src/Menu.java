import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO THE GAME!!! \n Please enter your username:");
        String username = scanner.nextLine();
        Player player = new Player(username);
        int choice;

        do {
            // Display Menu
            System.out.println("====== GAME MENU ======");
            System.out.println("1. Game 1");
            System.out.println("2. Game 2");
            System.out.println("3. Game 3");
            System.out.println("4. Game 4 - Dice Blackjack");
            System.out.println("5. View player info");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice! Enter a valid number.");
                System.out.print("Enter your choice: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Initializing Game 1.");
                    break;

                case 2:
                    System.out.println("Initializing Game 2.");
                    break;

                case 3:
                    System.out.println("Initializing Game 3.");
                    break;

                case 4:
                    System.out.println("Initializing Game 4.\n");
                    System.out.println("Please choose how much you want to stake: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid choice! Enter a valid number.");
                        System.out.print("Enter your choice: ");
                        scanner.next();
                    }
                    int stake = scanner.nextInt();
                    Game_4 game4 = new Game_4(stake,player);
                    game4.playGame(scanner);
                    break;

                case 5:
                    System.out.println("Player info:");
                    System.out.println("Username: " + player.getUsername());
                    System.out.println("Points: " + player.getPoints());
                    System.out.println("Games played: " + player.getNumberOfGamesPlayed());
                    System.out.println("Last played: " + player.getLastPlayedFormatted());
                    System.out.println("Highest score: " + Arrays.toString(player.getHighestScore()));
                    System.out.println("Recent score: " + Arrays.toString(player.getRecentScore()));
                    break;

                case 0:
                    System.out.println("Exiting game..");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 0–4.");
            }

            System.out.println();

        } while (choice != 0);
        scanner.close();
    }}
