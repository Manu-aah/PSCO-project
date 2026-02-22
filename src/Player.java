public class Player {
    private final String playerName;
    private int points;

    public Player(String playerName, int points) {
        this.playerName = playerName;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
