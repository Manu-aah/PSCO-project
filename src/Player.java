import java.time.LocalDateTime;

public class Player {
    private static String username ;
    private static int points;
    public static int[] highestScore = new int[4];
    public static int[] recentScore = new int[4];
    public static int numberOfGamesPlayed;
    public static LocalDateTime lastPlayed;

    public Player(String Username) {
        username = Username;
        points = 10;
        numberOfGamesPlayed = 0;
        for (int i : highestScore) {
            highestScore[i] = 0;
        }
        for (int i : recentScore) {
            recentScore[i] = 0;
        }
    }

    public static String getUsername() {
        return username;
    }

    public static int getPoints() {
        return points;
    }

    public static int[] getHighestScore() {
        return highestScore;
    }

    public static int[] getRecentScore() {
        return recentScore;
    }

    public static int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public static LocalDateTime getLastPlayed() {
        return lastPlayed;
    }

    public static void setPoints(int Points) {
        points = Points;
    }

    public static void setHighestScore(int[] HighestScore) {
        highestScore = HighestScore;
    }

    public static void setRecentScore(int[] RecentScore) {
        recentScore = RecentScore;
    }

    public static void setNumberOfGamesPlayed(int NumberOfGamesPlayed) {
        numberOfGamesPlayed = NumberOfGamesPlayed;
    }

    public static void setLastPlayed(LocalDateTime LastPlayed) {
        lastPlayed = LastPlayed;
    }

    public void getUserData(){
        System.out.println("Username: "+ username + "Points: "+ points + "numberOfGamesPlayed: " + numberOfGamesPlayed+ "LastPlayed: "+ lastPlayed+
                "HighestScore1: "+ highestScore[0] + "HighestScore2: "+ highestScore[1] + "HighestScore3: "+ highestScore[2] + "HighestScore4: "+ highestScore[3]);

    }
}
