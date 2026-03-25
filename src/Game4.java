import java.util.ArrayList;
import java.util.Scanner;

public class Game4 {
    private static final int GAME_INDEX = 3;
    ArrayList<Integer> dealersDice;
    int dealerTotal;
    ArrayList<Integer> playerDice;
    int playerTotal;
    int stake;
    Dice dice = new Dice();
    Player player;

    public Game4(int stake, Player player) {
        this.stake = stake;
        this.player = player;
        playerDice = new ArrayList<>();
        dealersDice = new ArrayList<>();
    }

    // Main game loop
    public void playGame(Scanner scanner) {
        //stake is deducted before the game starts
        if (player.getPoints() < stake) {
            System.out.println("Not enough points to play! You need " + stake + " points but have " + player.getPoints());
            return;
        }
        player.removePoints(stake);
        System.out.println("Stake of " + stake + " deducted. Points remaining: " + player.getPoints());

        //initial deal
        System.out.println("\n========== DICE BLACKJACK ==========");
        System.out.println("Rolling dice...\n");

        playerDice.add(dice.rollDice(1));
        playerDice.add(dice.rollDice(1));
        playerTotal = playerDice.get(0) + playerDice.get(1);

        //dealers roll
        dealersDice.add(dice.rollDice(1));
        dealersDice.add(dice.rollDice(1));
        dealerTotal = dealersDice.get(0) + dealersDice.get(1);

        System.out.println("Your dice:");
        dice.printDice(playerDice);
        System.out.println("Your total: " + playerTotal);

        System.out.println("\nDealer dice:");
        dice.printDice(dealersDice);
        System.out.println("Dealer total: " + dealerTotal);

        //Player's turn
        int i = 2;
        while (true) {
            System.out.println("\nHIT OR STAND?  (1 = Hit, 2 = Stand)");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter 1 or 2.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("\nHIT!");
                int roll = dice.rollDice(1);
                playerDice.add(roll);
                playerTotal += roll;
                System.out.println("You rolled:");
                dice.printDie(roll);
                System.out.println("Player total: " + playerTotal);
                i++;

                if (playerTotal > 21) {
                    System.out.println("\nBUST! YOU LOSE!!!");
                    resolveResult(-stake);
                    return;
                }

            } else if (choice == 2) {
                System.out.println("\nSTAND!");
                dealersTurn();

                if (dealerTotal > 21) {
                    System.out.println("DEALER BUST! YOU WIN! (+" + stake + ")");
                    resolveResult(stake);
                    return;
                } else if (playerTotal > dealerTotal) {
                    System.out.println("YOU WIN! Your " + playerTotal + " beats dealer's " + dealerTotal + ". (+" + stake + ")");
                    resolveResult(stake);
                    return;
                } else if (dealerTotal > playerTotal) {
                    System.out.println("YOU LOSE! Dealer's " + dealerTotal + " beats your " + playerTotal + ". (-" + stake + ")");
                    resolveResult(-stake);
                    return;
                } else {
                    System.out.println("TIE! Both scored " + playerTotal + ". Stake returned.");
                    resolveResult(0);
                    return;
                }
            } else {
                System.out.println("Invalid choice! Please enter 1 or 2.");
            }
        }
    }

    // Handles the outcome then adds winnings if the player won, returns stake on tie, nothing on loss
    // Then updates the player's game count and score record
    private int resolveResult(int result) {
        if (result > 0) {
            player.addPoints(result * 2); // return stake + winnings (stake was already deducted)
        } else if (result == 0) {
            player.addPoints(stake);      // tie: return the stake
        }
        // loss: nothing added as stake was already deducted upfront
        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.updateScores(GAME_INDEX, result);

        System.out.println("--- Points balance: " + player.getPoints() + " ---");
        System.out.println("Game Over!");
        return result;
    }

    // Dealer hits until their total is 17-21, or busts
    public void dealersTurn() {
        System.out.println("\nDealer's turn...");
        System.out.println("Dealer total so far: " + dealerTotal);

        if (dealerTotal >= 17) {
            System.out.println("Dealer stands.");
            return;
        }

        int i = 2;
        while (true) {
            int roll = dice.rollDice(1);
            dealersDice.add(roll);
            dealerTotal += roll;
            System.out.println("Dealer rolled:");
            dice.printDie(roll);
            System.out.println("Dealer total: " + dealerTotal);
            i++;

            if (dealerTotal > 21 || dealerTotal >= 17) {
                if (dealerTotal <= 21) System.out.println("Dealer stands.");
                return;
            }
        }
    }
}