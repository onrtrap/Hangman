import java.util.ArrayList;

public class Player {
    String playerName;
    int playerScore;
    public Player(String name, int score){
        playerName = name;
        playerScore = score;
    }

    public static Player parsePlayer(String input){
        return new Player(input.substring(0, input.indexOf("-")), Integer.parseInt(input.substring(input.indexOf(" ") + 1)));
    }
    public String giveScore(){
        return playerName + "-Score: " + playerScore;
    }

    public int getPlayerScore(){
        return playerScore;
    }

    public String getPlayerName(){
        return playerName;
    }

    public static void playerSort(ArrayList<Player> players) {
        int count = players.size();
        int smallestIndex;
        int smallest;
        for (int i = 0; i < count; i++) {
            smallest = players.get(i).getPlayerScore();
            smallestIndex = i;
            for (int j = i + 1; j < count; j++) {
                if (smallest > players.get(j).getPlayerScore()) {
                    smallest = players.get(j).getPlayerScore();
                    smallestIndex = j;
                }
            }
            if(smallestIndex == i);
            else{
                Player temp = players.get(i);
                players.set(i, players.get(smallestIndex));
                players.set(smallestIndex, temp);
            }
        }
    }
}
