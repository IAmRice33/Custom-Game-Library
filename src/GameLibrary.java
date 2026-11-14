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
            IO.println("Game name provided is null.");
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
     * Removes the game from the library matching the provided name.
     * Null values are rejected in case the game name is "null".
     * 
     */
    public void removeGame(String gameName) {
        if (gameName == null) {
            IO.println("Game name provided is null.");
            return;
        }

        if (library.contains(gameName)) {
            library.remove(gameName);
            IO.println(String.format("Successfully removed %s from the library.", gameName));
        } else {
            IO.println(String.format("%s was not found in the library.", gameName));
        }
    }

    /**
     * Checks if the game already exists in the game library.
     * Checks are case insensitive.
     * Duplicate names are checked to prevent accidental replicating of names when 
     * the game library becomes large, and games with identical names are rare.
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
