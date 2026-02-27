import java.util.Random;

public abstract class Dice {
    Random random = new Random();
    int total;

    public int rollDice(int numOfDice) {
        total = 0;
        for (int i = 0; i < numOfDice; i++){
            total += random.nextInt(1,7);
        }
        return total;
    }
}
