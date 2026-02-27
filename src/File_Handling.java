import java.io.*; //
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class File_Handling {
    private String path = "src/Player_Data.txt";

    public void saveData(Player player) throws IOException {
        player.setLastPlayed(LocalDateTime.now());
        FileWriter writer = new FileWriter(path);
        writer.write(player.getUsername() + "," + player.getPoints() + "," + player.getNumberOfGamesPlayed() + "," + player.getLastPlayedFormatted());

        for (int score : player.getHighestScore()) {
            writer.write("," + score);
        }
        for (int score : player.getRecentScore()) {
            writer.write("," + score);
        }
        writer.write("\n");
        writer.close();
    }

    public void loadData(Player player) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        String[] lineDat = line.split(",");
        player.setUsername(lineDat[0]);
        player.setPoints(Integer.parseInt(lineDat[1]));
        player.setNumberOfGamesPlayed(Integer.parseInt(lineDat[2]));
        player.setLastPlayed(LocalDateTime.parse(lineDat[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        int j = 4;
        int[] highest = player.getHighestScore();
        for (int i = 0; i < highest.length; i++) {
            highest[i] = Integer.parseInt(lineDat[j++]);
        }
        int[] recent = player.getRecentScore();
        for (int i = 0; i < recent.length; i++) {
            recent[i] = Integer.parseInt(lineDat[j++]);
        }
    }

}

