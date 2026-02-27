import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;

public class Player {

    private String username;
    private int points;
    private int[] highestScore = new int[4];
    private int[] recentScore = new int[4];
    private int numberOfGamesPlayed;
    private LocalDateTime lastPlayed;

    public Player(String username) {
        this.username = username;
        this.points = 10;
        this.numberOfGamesPlayed = 0;
        Arrays.fill(highestScore, 0); // simpler to write this instead of using a for loop for both scores **the Arrays class needed to be imported for this method
        Arrays.fill(recentScore, 0);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

    public void setHighestScore(int[] highestScore) {
        this.highestScore = highestScore;
    }

    public int[] getRecentScore() {
        return recentScore;
    }

    public void setRecentScore(int[] recentScore) {
        this.recentScore = recentScore;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public LocalDateTime getLastPlayed() {
        return lastPlayed;
    }

    public void addPoints(int amount) {
        points += amount;
    }
    public void removePoints(int amount) {
        points -= amount;
        if (points < 0) points = 0;
    }

    public void setLastPlayed(LocalDateTime lastPlayed) {
        this.lastPlayed = lastPlayed;
    }


    public String getLastPlayedFormatted() {
        if (lastPlayed == null) {
            return "Player has not played yet.";
        }
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("mm:ss/dd/MM/yyyy");
        return lastPlayed.format(formatter);
    }

}