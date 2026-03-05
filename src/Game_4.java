import java.util.ArrayList;
import java.util.Scanner;
public class Game_4 {
    ArrayList<Integer> dealersDice;
    int dealerTotal;
    ArrayList<Integer> playerDice;
    int playerTotal;
    int Stake;
    Dice dice = new Dice();

    public Game_4(int Stake){
        this.Stake = Stake;
        playerDice = new ArrayList<>();
        dealersDice = new ArrayList<>();
    }
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;

        System.out.println("Rolling dice....");
        playerDice.add(dice.rollDice(1));
        System.out.println("First dice: " + playerDice.get(0));
        playerDice.add(dice.rollDice(1));
        System.out.println("Second dice:" + playerDice.get(1));
        playerTotal = playerDice.get(0) + playerDice.get(1);
        System.out.println("Player total: " + playerTotal);

        System.out.println("Dealers turn....");           //dealers turn
        System.out.println("Rolling dice....");
        dealersDice.add(dice.rollDice(1));
        System.out.println("First dice: " + dealersDice.get(0));
        dealersDice.add(dice.rollDice(1));
        System.out.println("Second dice:" + dealersDice.get(1));
        dealerTotal = dealersDice.get(0) + dealersDice.get(1);
        System.out.println("Dealer total: " + dealerTotal);

        int i = 2;
        while (!finished) {
            System.out.println("HIT OR STAND?\nPress 1 to hit or 2 to stand.");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid choice! Please enter 1 or 2.");
                scanner.next();     //this ignored the invalid input and moves to the next input. This is for a simple int validation step
                continue;
            }

            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("HIT!!!");
                playerDice.add(dice.rollDice(1));
                int currentRoll = playerDice.get(i);
                playerTotal += currentRoll;
                System.out.println("You rolled: " + currentRoll);
                System.out.println("Player total: " + playerTotal);
                i++;
                if (playerTotal>21) {
                    System.out.println("BUST! YOU LOSE!");
                    finished = true;
                }

            } else if (choice == 2) {
                System.out.println("STAND!!!");
                dealersTurn();
                if(dealerTotal>21){
                    System.out.println("DEALER BUST! YOU WIN!");
                } else if(dealerTotal>playerTotal){
                    System.out.println("YOU LOSE!!! Dealers roll is closer to 21");
                } else System.out.println("YOU WIN!!!");
                finished = true;
            } else {
                throw new IllegalArgumentException("Invalid choice! Please enter 1 or 2.");
            }
        } scanner.close();
        System.out.println("Game Over!");
    }
    public void dealersTurn(){
        int i = 2;
        boolean bust = false;
        while(!bust){
            dealersDice.add(dice.rollDice(1));
            dealerTotal += dealersDice.get(i);
            System.out.println("Dealer rolled: " + dealersDice.get(i));
            System.out.println("Dealer total: " + dealerTotal);
            i++;
            if (dealerTotal>17 && dealerTotal<=21) return;
            if (dealerTotal>21) {
                bust = true;
            }
        }
    }
}
