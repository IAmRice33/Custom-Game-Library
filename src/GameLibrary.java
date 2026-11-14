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
            IO.println("Game name provided to add is null.");
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
     * Removes the game from the library matching the requested case insensitive name.
     * Prints the result to the terminal and null values are rejected.
     * 
     * @param gameName
     */
    public void removeGame(String gameName) {
        if (gameName == null) {
            IO.println("Game name provided for removal is null.");
            return;
        }

        int index = gameIndex(gameName);

        // Use index instead of passing the exact string to make removal case insensitive.
        if (index != -1) {
            library.remove(index);
            IO.println(String.format("Successfully removed %s from the library.", gameName));
        } else {
            IO.println(String.format("%s was not found in the library.", gameName));
        }
    }

    /**
     * Checks if the requested case insensitive game name already exists 
     * in the game library.
     * Duplicate names are checked to prevent accidental replicating of names when 
     * the game library becomes large, and games with identical names are rare.
     * 
     * @param gameName
     * @return true if the game exists in library, case insensitive, false otherwise
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

    /**
     * Returns the index of the first occurance of the requested game name
     * in the game library, or -1 if there is no such game.
     * Comparisons between library games and the requested name are case
     * insensitive.
     *  
     * @param gameName
     * @return the index of the first occurance of requested game, or -1
     * if the game is not in the library
     */
    private int gameIndex(String gameName) {
        for (int i = 0; i < library.size(); i++) {
            // Compare both strings in lowercase to ignore case sensitivity
            if (library.get(i).toLowerCase().equals(gameName.toLowerCase())) {
                return i;
            }
        }

        return -1;
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
