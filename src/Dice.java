import java.util.ArrayList;
import java.util.Random;

public class Dice {
    Random random = new Random();
    int total;

    private static final String[][] DICE_FACES = {
            {}, // no dice roll for 0
            {
                    "+-------+",
                    "|       |",
                    "|   *   |",
                    "|       |",
                    "+-------+"
            },
            {
                    "+-------+",
                    "| *     |",
                    "|       |",
                    "|     * |",
                    "+-------+"
            },
            {
                    "+-------+",
                    "| *     |",
                    "|   *   |",
                    "|     * |",
                    "+-------+"
            },
            {
                    "+-------+",
                    "| *   * |",
                    "|       |",
                    "| *   * |",
                    "+-------+"
            },
            {
                    "+-------+",
                    "| *   * |",
                    "|   *   |",
                    "| *   * |",
                    "+-------+"
            },
            {
                    "+-------+",
                    "| *   * |",
                    "| *   * |",
                    "| *   * |",
                    "+-------+"
            }
    };
    public int rollDice(int numOfDice) {
        total = 0;
        for (int i = 0; i < numOfDice; i++) {
            total += random.nextInt(1, 7);
        }
        return total;
    }
    // Prints a single die face
    public void printDie(int value) {
        for (String row : DICE_FACES[value]) {
            System.out.println(row);
        }
    }
    // Prints multiple dice side by side from an int array
    public void printDice(int[] values) {
        for (int row = 0; row < 5; row++) {
            for (int d = 0; d < values.length; d++) {
                System.out.print(DICE_FACES[values[d]][row]);
                if (d < values.length - 1) System.out.print("  ");
            }
            System.out.println();
        }
    }
    // Prints multiple dice side by side from an ArrayList
    public void printDice(ArrayList<Integer> values) {
        for (int row = 0; row < 5; row++) {
            for (int d = 0; d < values.size(); d++) {
                System.out.print(DICE_FACES[values.get(d)][row]);
                if (d < values.size() - 1) System.out.print("  ");
            }
            System.out.println();
        }
    }
    // Prints a 3x3 grid of dice, empty cells shown as blank dice
    public void printDiceGrid(int[][] grid) {
        for (int i = 0; i < 3; i++) {
            for (int faceRow = 0; faceRow < 5; faceRow++) {
                for (int j = 0; j < 3; j++) {
                    int val = grid[i][j];
                    if (val == 0) {
                        if (faceRow == 0 || faceRow == 4) System.out.print("+-------+");
                        else                               System.out.print("|       |");
                    } else {
                        System.out.print(DICE_FACES[val][faceRow]);
                    }
                    if (j < 2) System.out.print("  ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}