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

    /**
     * Adds a game to the library.
     * Prevents duplicate names and prints result to terminal.
     * Rejects null values.
     * 
     * @param gameName
     */
    public void addGame(String gameName) {
        if (gameName == null) {
            IO.println("Game provided is null.");
            return;
        }

        if (hasDuplicate(gameName)) {
            IO.println(String.format("Failed to add %s: Game already exists.", gameName));
        } else {
            library.add(gameName);
            IO.println(String.format("Successfully added %s.", gameName));
        }
    }

    /**
     * Checks if the game already exists in the game library.
     * Checks are case insensitive.
     * 
     * @param gameName
     * @return if the game exists in library, case insensitive
     */
    private boolean hasDuplicate(String gameName) {
        for (String game : library) {
            // Compare both strings in lowercase to ignore case sensitivity
            if (game.toLowerCase().equals(gameName.toLowerCase())) {
                return true;
            }
        }
        return false;
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
