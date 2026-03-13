import java.util.ArrayList;
import java.util.Scanner;

public class Game_4 {
    private static final int GAME_INDEX = 3;   // Game 4 = index 3 in the scores arrays
    ArrayList<Integer> dealersDice;
    int dealerTotal;
    ArrayList<Integer> playerDice;
    int playerTotal;
    int stake;
    Dice dice = new Dice();
    Player player;

    public Game_4(int stake, Player player) {
        this.stake = stake;
        this.player = player;
        playerDice = new ArrayList<>();
        dealersDice = new ArrayList<>();
    }

    public void playGame(Scanner scanner) {

        //stake is deducted before the game starts
        if (player.getPoints() < stake) {
            System.out.println("Not enough points to play! You need " + stake + " points but have " + player.getPoints());
            return;
        }
        player.removePoints(stake);
        System.out.println("Stake of " + stake + " deducted. Points remaining: "
                + player.getPoints());

        //initial deal
        System.out.println("\n---- BLACKJACK ----");
        System.out.println("Rolling dice...");
        playerDice.add(dice.rollDice(1));
        playerDice.add(dice.rollDice(1));
        playerTotal = playerDice.get(0) + playerDice.get(1);
        System.out.println("Your dice:   " + playerDice.get(0) + " + " + playerDice.get(1) + " = " + playerTotal);
        //dealers roll
        dealersDice.add(dice.rollDice(1));
        dealersDice.add(dice.rollDice(1));
        dealerTotal = dealersDice.get(0) + dealersDice.get(1);
        System.out.println("Dealer dice: " + dealersDice.get(0) + " + " + dealersDice.get(1) + " = " + dealerTotal);

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
                System.out.println("HIT!");
                playerDice.add(dice.rollDice(1));
                int currentRoll = playerDice.get(i);
                playerTotal += currentRoll;
                System.out.println("You rolled: " + currentRoll + "  |  Player total: " + playerTotal);
                i++;

                if (playerTotal > 21) {
                    System.out.println("BUST! YOU LOSE!!!");
                    resolveResult(-stake);
                    return;
                }

            } else if (choice == 2) {
                System.out.println("STAND!");
                dealersTurn();

                if (dealerTotal > 21) {
                    System.out.println("DEALER BUST! YOU WIN! (+" + stake + ")");
                    resolveResult(stake);
                    return;
                } else if (playerTotal > dealerTotal) {
                    System.out.println("YOU WIN! Your " + playerTotal
                            + " beats dealer's " + dealerTotal + ". (+" + stake + ")");
                    resolveResult(stake);
                    return;
                } else if (dealerTotal > playerTotal) {
                    System.out.println("YOU LOSE! Dealer's " + dealerTotal
                            + " beats your " + playerTotal + ". (-" + stake + ")");
                    resolveResult(-stake);
                    return;
                } else {
                    System.out.println("TIE! Both scored " + playerTotal
                            + ". Stake returned.");
                    resolveResult(0);
                    return;
                }

            } else {
                System.out.println("Invalid choice! Please enter 1 or 2.");
            }
        }
    }

    private int resolveResult(int result) {
        if (result > 0) {
            player.addPoints(result * 2); // return stake + winnings (stake was already deducted)
        } else if (result == 0) {
            player.addPoints(stake);      // tie: return the stake
        }
        // loss: nothing added — stake was already deducted upfront

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
            dealersDice.add(dice.rollDice(1));
            dealerTotal += dealersDice.get(i);
            System.out.println("Dealer rolled: " + dealersDice.get(i) + "  |  Dealer total: " + dealerTotal);
            i++;

            if (dealerTotal > 21 || dealerTotal >= 17) {
                if (dealerTotal <= 21) System.out.println("Dealer stands.");
                return; // gets out of the loop, bust
            }
        }
    }
}