import java.util.Scanner; //

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display Menu
            System.out.println("====== GAME MENU ======");
            System.out.println("1. Game 1");
            System.out.println("2. Game 2");
            System.out.println("3. Game 3");
            System.out.println("4. Game 4");
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
                    System.out.println("Initializing Game 4.");
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
