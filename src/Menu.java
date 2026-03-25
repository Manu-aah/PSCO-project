import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO THE GAME!!! \n Please enter your username:");
        String username = scanner.nextLine();
        Player player = new Player(username);
        FileHandling fileHandling = new FileHandling();
        try {
            if (fileHandling.playerExists(username)) {
                fileHandling.loadData(player);
                System.out.println("Welcome back, " + username + "! Your data has been loaded.");
            } else {
                System.out.println("Welcome, " + username + "! Starting fresh.");
            }
        } catch (IOException e) {
            System.out.println("Could not load player data: " + e.getMessage());
        }
        int choice;

        do {
            // Display Menu
            System.out.println("------ || GAME MENU ||------");
            System.out.println("1. Game 1- DICE PATTERNS CHALLENGE");
            System.out.println("2. Game 2 - DICE GRID PUZZLE");
            System.out.println("3. Game 3 - DICE CODEBREAKER");
            System.out.println("4. Game 4 - DICE BLACKJACK");
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
                    System.out.println("Starting Game 1.");
                    Game1 game1 = new Game1(player);
                    game1.playGame(scanner);
                    break;

                case 2:
                    System.out.println("Starting Game 2.");
                    Game2 game2 = new Game2(player);
                    game2.play(scanner);
                    try {
                        fileHandling.saveData(player);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    System.out.println("Starting Game 3.");
                    Game3 game3 = new Game3(player);
                    game3.playGame(scanner);
                    try {
                        fileHandling.saveData(player);
                    } catch (IOException e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Starting Game 4.\n");
                    int stake;
                    while (true) {
                        System.out.print("Please choose how much you want to stake (0 or more): ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input! Enter a valid number.");
                            scanner.next();
                        }
                        stake = scanner.nextInt();
                        if (stake >= 0) break;
                        System.out.println("Stake cannot be negative.");
                    }
                    Game4 game4 = new Game4(stake, player);
                    game4.playGame(scanner);
                    try {
                        fileHandling.saveData(player);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
    }
}