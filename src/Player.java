import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Player {
    private final String username;
    private int points;
    private int[] highestScore = new int[4];
    private int[] recentScore = new int[4];
    private int numberOfGamesPlayed;
    private LocalDateTime lastPlayed;

    public Player(String username) {
        this.username = username;
        this.points = 10;
        numberOfGamesPlayed = 0;
        for (int i : highestScore) {
            highestScore[i] = 0;
        }
        for (int i : recentScore) {
            recentScore[i] = 0;
        }
    }

    public void saveToFile() throws IOException {
        lastPlayed = LocalDateTime.now();
        PrintWriter writer =
                new PrintWriter(new FileWriter("src/Player_Data.txt"));
        writer.print(username + "," + points + "," + numberOfGamesPlayed + "," + lastPlayed);

        for (int score : highestScore) {
            writer.print("," + score);
        }

        for (int score : recentScore) {
            writer.print("," + score);
        }
        writer.println();
        writer.close();
    }

    public void loadFromFile() throws IOException {
        File file = new File("src/Player_Data.txt");

        if (!file.exists()) {
            return;
        }
        BufferedReader reader =
                new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        reader.close();

        if (line == null) return;

        String[] data = line.split(",");

        if (!data[0].equals(username)) {
            throw new IllegalStateException("Player data does not match");
        }
        points = Integer.parseInt(data[1]);
        lastPlayed = LocalDateTime.parse(data[2]);

        int index = 3;
        for (int i = 0; i < highestScore.length; i++) {
            highestScore[i] = Integer.parseInt(data[index++]);
        }
        for (int i = 0; i < recentScore.length; i++) {
            recentScore[i] = Integer.parseInt(data[index++]);
        }
    }

    public String getLastPlayedFormatted() {
        if (lastPlayed == null) {
            return "Never played";
        }
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return lastPlayed.format(formatter);
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public void getUserData(){
        System.out.println( username + "," + points + "," + numberOfGamesPlayed + "," + highestScore[0] + "," + highestScore[1] + "," + highestScore[2] + "," + highestScore[3]
                + "," + recentScore[0] + "," + recentScore[1] + "," + recentScore[2] + "," + recentScore[3] + "," + getLastPlayedFormatted());
    }
}
