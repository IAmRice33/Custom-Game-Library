import java.util.ArrayList;

/**
 * 
 * GameLibrary
 * 
 * Manages a game library for games.
 * 
 * @author Kyle Yang
 * 
 * @since 1.0
 */
public class GameLibrary {
    // Use ArrayList<String> to dynamically store games
    private ArrayList<String> library;

    public GameLibrary() {
        library = new ArrayList<>();
    }

    public void addGame(String gameName) {
        if (library.contains(gameName)) {
            IO.println(String.format("Failed to add %s: Game already exists.", gameName));
        } else {
            library.add(gameName);
            IO.println(String.format("Successfully added %s.", gameName));
        }
    }

    @Override 
    public String toString() {
        String gameList = "";

        for (String gameName : library) {
            gameList += gameName + " ";
        }

        return gameList;
    }
}
