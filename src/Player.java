import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Player {

    private String username;
    private int points;
    private int[] highestScore = new int[4];
    private int[] recentScore = new int[4];
    private int numberOfGamesPlayed;
    private LocalDateTime lastPlayed;

    // Initializes a new player with 10 starting points and zeroes score arrays
    public Player(String username) {
        this.username = username;
        this.points = 10;
        this.numberOfGamesPlayed = 0;
        Arrays.fill(highestScore, 0);
        Arrays.fill(recentScore, 0);
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int[] getHighestScore() {
        return highestScore;
    }

    public int[] getRecentScore() {
        return recentScore;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public void setLastPlayed(LocalDateTime lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    // Adds points to the player's total
    public void addPoints(int amount) {
        points += amount;
    }

    // Removes points from the player's total, floors at 0 so points can never go negative
    public void removePoints(int amount) {
        points -= amount;
        if (points < 0) points = 0;
    }

    // Updates the recent score for the given game and replaces the highest score if beaten
    // gameIndex 0-3 maps to Game1-Game4
    public void updateScores(int gameIndex, int score) {
        if (gameIndex < 0 || gameIndex > 3) return;
        recentScore[gameIndex] = score;
        if (score > highestScore[gameIndex]) {
            highestScore[gameIndex] = score;
        }
    }

    // Returns the last played time as a formatted string, or a default message if never played
    public String getLastPlayedFormatted() {
        if (lastPlayed == null) {
            return "Player has not played yet.";
        }
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        return lastPlayed.format(formatter);
    }
}