import java.io.*; //
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class File_Handling {

    public void saveToFile() throws IOException {
        Player.setLastPlayed(LocalDateTime.now());
        FileWriter writer =  new FileWriter("src/Player_Data.txt");
        writer.write(Player.getUsername() + "," + Player.getPoints() + "," + Player.numberOfGamesPlayed + "," + Player.lastPlayed);

        for (int score : Player.highestScore) {
            writer.write("," + score);
        }

        for (int score : Player.recentScore) {
            writer.write("," + score);
        }
        writer.write("\n");
        writer.close();
    }

    public void loadFromFile() throws IOException {
        File file = new File("src/Player_Data.txt");

        if (!file.exists()) {
            return;
        }
       BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        reader.close();

        if (line == null) return;

        String[] data = line.split(",");

        if (!data[0].equals(Player.getUsername())) {
            throw new IllegalStateException("Player data does not match"); // Because we're using the player name as a header for the data we need to check if it matches.
        }
        Player.setPoints(Integer.parseInt(data[1]));
        Player.lastPlayed = LocalDateTime.parse(data[2]);

        int index = 3;
        for (int i = 0; i < Player.highestScore.length; i++) {
            Player.highestScore[i] = Integer.parseInt(data[index++]);
        }
        for (int i = 0; i < Player.recentScore.length; i++) {
            Player.recentScore[i] = Integer.parseInt(data[index++]);
        }
    }

    public String getLastPlayedFormatted() {
        if (Player.lastPlayed == null) {
            return "Never played";
        }
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return Player.lastPlayed.format(formatter);
    }
}
