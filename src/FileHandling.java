import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {
    private String path = "src/Player_Data.txt";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

    // Saves the player's current data. if the player already exists in the file their line is
    // replaced, otherwise a new line is appended
    public void saveData(Player player) throws IOException {
        player.setLastPlayed(LocalDateTime.now());
        String updatedLine = buildLine(player);

        File file = new File(path);
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Read existing lines if the file already exists
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    String existingUsername = line.split(",")[0];
                    if (existingUsername.equals(player.getUsername())) {
                        lines.add(updatedLine); //replace this players line
                        found = true;
                    } else {
                        lines.add(line); //keep all other players
                    }
                }
            }
            reader.close();
        }

        if (!found) {
            lines.add(updatedLine); //new player
        }

        // Write all lines back
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    // Loads data for the given player by matching their username in the file
    public void loadData(Player player) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) continue;
            String[] data = line.split(",");

            if (data[0].equals(player.getUsername())) {
                reader.close();
                parseLine(data, player);
                return;
            }
        }

        reader.close();
        throw new IllegalArgumentException("No saved data found for username: " + player.getUsername());
    }

    // Checks whether a username already has a saved line in the file
    public boolean playerExists(String username) throws IOException {
        File file = new File(path);
        if (!file.exists()) return false;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank() && line.split(",")[0].equals(username)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    // Builds a CSV line from the player's current data with username, points, games played,
    // last played, then highest scores and recent scores for each game
    private String buildLine(Player player) {
        String line = player.getUsername() + "," + player.getPoints() + "," + player.getNumberOfGamesPlayed() + "," + player.getLastPlayedFormatted();

        for (int score : player.getHighestScore()) {
            line += "," + score;
        }
        for (int score : player.getRecentScore()) {
            line += "," + score;
        }
        return line;
    }

    // Parses a split CSV line back into the Player object, restoring all fields including
    // highest and recent scores for each game
    private void parseLine(String[] data, Player player) {
        player.setPoints(Integer.parseInt(data[1]));
        player.setNumberOfGamesPlayed(Integer.parseInt(data[2]));
        player.setLastPlayed(LocalDateTime.parse(data[3], FORMATTER));

        int j = 4;
        int[] highest = player.getHighestScore();
        for (int i = 0; i < highest.length; i++) {
            highest[i] = Integer.parseInt(data[j++]);
        }
        int[] recent = player.getRecentScore();
        for (int i = 0; i < recent.length; i++) {
            recent[i] = Integer.parseInt(data[j++]);
        }
    }
}