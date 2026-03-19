import java.time.LocalDateTime;
import java.util.*;

public class Game_1 {
    private Player player;
    private Dice dice = new Dice();
    private int[] diceValues = new int[5];
    private int remainingRolls = 2;
    private boolean gameOver = false;

    public Game_1(Player player) {
        this.player = player;
    }

    public void initialRoll() {
        for (int i = 0; i < 5; i++) {
            diceValues[i] = dice.rollDice(1);
        }
    }

    public List<String> reroll(String positionInput) {
        List<String> errors = new ArrayList<>();
        String[] parts = positionInput.trim().split("\\s+");
        boolean anyRerolled = false;

        for (String posStr : parts) {
            try {
                int pos = Integer.parseInt(posStr.trim());
                if (pos >= 1 && pos <= 5) {
                    diceValues[pos - 1] = dice.rollDice(1);
                    anyRerolled = true;
                } else {
                    errors.add("Invalid position: " + pos + " (must be 1-5)");
                }
            } catch (NumberFormatException e) {
                errors.add("Invalid input: '" + posStr + "'");
            }
        }

        if (anyRerolled) remainingRolls--;
        return errors;
    }

    // Finishes the game, calculates score, updates player
    public int finalise() {
        int score = evaluateScore(diceValues);
        player.addPoints(score);
        player.setNumberOfGamesPlayed(player.getNumberOfGamesPlayed() + 1);
        player.setLastPlayed(LocalDateTime.now());
        player.updateScores(0, score);
        gameOver = true;
        return score;
    }

    public int[] getDice() { return diceValues; }
    public int getRemainingRolls() { return remainingRolls; }
    public boolean isGameOver() { return gameOver; }

    public String getDiceString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < diceValues.length; i++) {
            sb.append(diceValues[i]);
            if (i < diceValues.length - 1) sb.append(" | ");
        }
        return sb.toString();
    }

    public String evaluateScoreName(int[] dice) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int die : dice) counts.put(die, counts.getOrDefault(die, 0) + 1);

        boolean has3 = false, has2 = false;
        int pairCount = 0;

        for (int count : counts.values()) {
            if (count == 5) return "Five of a Kind! Score: 50";
            if (count == 4) return "Four of a Kind! Score: 40";
            if (count == 3) has3 = true;
            if (count == 2) { has2 = true; pairCount++; }
        }
        if (has3 && has2) return "Full House! Score: 35";

        int[] sorted = dice.clone();
        Arrays.sort(sorted);
        if (Arrays.equals(sorted, new int[]{1,2,3,4,5}) || Arrays.equals(sorted, new int[]{2,3,4,5,6}))
            return "Straight! Score: 30";

        if (has3) return "Three of a Kind! Score: 25";
        if (pairCount == 2) return "Two Pair! Score: 20";
        if (has2) return "One Pair! Score: 10";
        return "No match. Score: 0";
    }

    private int evaluateScore(int[] dice) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int die : dice) counts.put(die, counts.getOrDefault(die, 0) + 1);

        boolean has3 = false, has2 = false;
        int pairCount = 0;

        for (int count : counts.values()) {
            if (count == 5) return 50;
            if (count == 4) return 40;
            if (count == 3) has3 = true;
            if (count == 2) { has2 = true; pairCount++; }
        }
        if (has3 && has2) return 35;

        int[] sorted = dice.clone();
        Arrays.sort(sorted);
        if (Arrays.equals(sorted, new int[]{1,2,3,4,5}) || Arrays.equals(sorted, new int[]{2,3,4,5,6}))
            return 30;

        if (has3) return 25;
        if (pairCount == 2) return 20;
        if (has2) return 10;
        return 0;
    }
}