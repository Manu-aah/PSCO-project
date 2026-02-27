import java.util.ArrayList;
import java.util.Scanner;
public class Game_4 {
    ArrayList<Integer> dealersDice;
    int dealerTotal;
    ArrayList<Integer> playerDice;
    int playerTotal;
    int minimumStake;
    Dice dice = new Dice();

    public Game_4(int minimumStake){
        this.minimumStake = minimumStake;
        playerDice = new ArrayList<>();
        dealersDice = new ArrayList<>();
    }
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean bust = false;

        System.out.println("Rolling dice....");
        playerDice.add(dice.rollDice(1));

        System.out.println("\nFirst DIce: " + playerDice.getFirst());
        playerDice.add(dice.rollDice(1));
        System.out.println("\nSecond Dice:" + playerDice.get(1));
        playerTotal = playerDice.get(0) + playerDice.get(1);

        System.out.println("\nDealers turn....");
        System.out.println("Rolling dice....");
        dealersDice.add(dice.rollDice(1));
        System.out.println("\nFirst DIce: " + dealersDice.getFirst());
        dealersDice.add(dice.rollDice(1));
        System.out.println("\nSecond Dice:" + dealersDice.get(1));
        dealerTotal = dealersDice.get(0) + dealersDice.get(1);

        int i = 2;
        while(!bust){
            int choice = scanner.nextInt();
            if (choice > 0 && choice < 3) {
                switch (choice) {
                    case 1:
                        System.out.println("Hit!");
                        playerDice.add(dice.rollDice(1));
                        playerTotal += playerDice.get(i);
                        System.out.println(playerTotal);
                        i++;
                        if (playerTotal > 21){
                            System.out.println("BUST!");
                            bust = true;
                        };
                        break;
                    case 2:
                        System.out.println("Stand!");
                        dealersTurn();
                        if(dealerTotal>playerTotal && dealerTotal<=21) System.out.println("YOU LOSE!!!");
                        else System.out.println("YOU WIN!!!");
                        break;
                }
        } else;
            }
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
                System.out.println("BUST!");
                bust = true;
                dealerTotal = -1;
            }
        }
    }
}
