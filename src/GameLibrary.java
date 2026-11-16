import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
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
    // Use ArrayList<String> to dynamically store games.
    private final ArrayList<String> library;
    // This is the default path to save the data to.
    private static final String DEFAULT_SAVE_PATH = "/data/GameLibrary.txt";

    public GameLibrary() {
        library = new ArrayList<>();
    }

    /**
     * Adds a game to the library.
     * Prevents duplicate names and prints result to terminal.
     * Rejects null values.
     * 
     * @param gameName the game to add
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
     * @param gameName the game to remove
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
     * Displays game library contents from the start index (1-indexed) inclusize 
     * to the end index inclusive to the terminal numbered in the order that each
     * game occurs in the library. Each game is printed on a new line.
     * 
     * @param start the starting index, must exist between 1 and the library size - 1 inclusive
     * @param end the ending index, must exist between {@code start + 1} 
     * and the library size inclusive
     * @throws IndexOutOfBoundsException
     */
    public void displayLibrary(int start, int end) throws IndexOutOfBoundsException {
        // Check first to prevent throwing an exception on an empty library;
        if (library.isEmpty()) {
            IO.println("Game library is empty.");
            return;
        }

        if (start < 1) {
            throw new IndexOutOfBoundsException("Library begins at index 1");
        }

        if (end <= start) {
            throw new IndexOutOfBoundsException("Display must end at least 1 after start");
        }

        if (start >= library.size() || end > library.size()) {
            throw new IndexOutOfBoundsException(String.format("Game library has size %d", library.size()));
        }

        // Create a separator on both ends to make output clear.
        // Format a title at the top, and use left and right justified widths to ensure spacing.
        IO.println(String.format("%-20s%s%20s", "-".repeat(19), "Game Library", "-".repeat(19)));

        // Print each game name on a new line and number it based on position in library.
        // Use 1-index to align user input of start and end with the displayed numbers
        // next to the games. Start at start - 1 as number printed is i + 1.
        for (int i = start - 1; i < end; i++) {
            IO.println(String.format("%d. %s", (i + 1), library.get(i)));
        }

        // Bottom separator
        IO.println(String.format("%52s", "-".repeat(52)));
    }

    /**
     * Displays the full contents of the game library.
     * 
     * @see #displayLibrary(int, int)
     */
    public void displayLibrary() {
        displayLibrary(1, library.size());
    }

    /**
     * Create a file at the supplied file path and save the game library's data 
     * in this file. This method exists if the user wants to use a custom file path.
     * 
     * @param filePath the file path to save the game library's data at
     */
    public void saveLibrary(String filePath) {
        // Create a new file using the path from the project root rather than src.
        // Use relative paths to keep the project portable.
        Path path;
        try {
            path = Path.of("..", filePath);
        } catch (InvalidPathException e) {
            IO.println("Could not convert path string to a file path");
            return;
        }
        
        try {
            // Create the parent directory first if it does exist.
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            IO.println("Could not create directory: " + e.getMessage());
        }

        try {
            Files.createFile(path);
            IO.println(String.format("Created %s", filePath));
        } catch (FileAlreadyExistsException e) {
            // Keep the output simple as this is not an actual error.
            IO.println(String.format("File %s exists", filePath));
        } catch (IOException e) {
            IO.println(String.format("Could not create file %s: %s", filePath, e.getMessage()));
        }

        try (PrintWriter writer = new PrintWriter("../" + filePath)) {
            for (String game : library) {
                writer.println(game);
            }
            writer.flush();
            IO.println("Save complete.");
        } catch (FileNotFoundException e) {
            IO.println(String.format("Could not find file %s.", filePath));
        }
    }

    /**
     * Saves the game library's data to the default save file path
     * of {@value #DEFAULT_SAVE_PATH} from the root directory.
     * 
     * @see #saveLibrary(String)
     */
    public void saveLibrary() {
        saveLibrary(DEFAULT_SAVE_PATH);
    }

    /**
     * Loads game library data from the supplied file path. 
     * This method exists if the user decides to load data from a custom file path.
     * 
     * @param filePath the file path to load data from
     */
    public void loadLibrary(String filePath) {
        // Empty the library to keep game libraries unique.
        library.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("../" + filePath))) {
            String game;
            while ((game = reader.readLine()) != null) {
                // Use addGame to uphold the game library's rules.
                addGame(game);
            }
            IO.println("Load Complete.");
        } catch (IOException e) {
            IO.println("Could not load from file: " + filePath);
        }
    }

    /**
     * Loads the game library's data from the default save file path
     * of {@value #DEFAULT_SAVE_PATH} from the root directory.
     * 
     * @see #loadLibrary(String)
     */
    public void loadLibrary() {
        loadLibrary(DEFAULT_SAVE_PATH);
    }

    /**
     * Checks if the requested case insensitive game name already exists 
     * in the game library.
     * Duplicate names are checked to prevent accidental replicating of names when 
     * the game library becomes large, and games with identical names are rare.
     * 
     * @param gameName the game to check for
     * @return true if the game exists in library, case insensitive, false otherwise
     */
    private boolean hasDuplicate(String gameName) {
        for (String game : library) {
            // Compare both strings in lowercase to ignore case sensitivity.
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
     * @param gameName the game to find
     * @return the index of the first occurance of requested game, or -1
     * if the game is not in the library
     */
    private int gameIndex(String gameName) {
        for (int i = 0; i < library.size(); i++) {
            // Compare both strings in lowercase to ignore case sensitivity.
            if (library.get(i).toLowerCase().equals(gameName.toLowerCase())) {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * Prints a list of commands and their arguments to the terminal.
     */
    public void printCommands() {
        IO.println("List of Available Commands");
        IO.println("Please keep all arguments space separated and case sensitive.");
        IO.println("Add {Game1 Game2 ...}: Adds listed games to the library");
        IO.println("Remove {Game1 Game2 ...}: Removes listed games from the library");
        IO.println("Check {Game1 Game2 ...}: Checks for listed games in the library");
        IO.println("Find {Game1 Game2 ...}: Finds the index of the listed games in the library");
        IO.println(
            "Display opt.{start end}: Displays the library from the start index " + 
            "to the end index (no additional arguments displays the entire library)."
        );
        IO.println(String.format("Save (opt. filepath): Saves the library in the given filepath " +
            "(no additional argument saves to %s), overwrites the file.", DEFAULT_SAVE_PATH)
        );
        IO.println(String.format("Load (opt. filepath): Loads the library from the given filepath " +
            "(no additional argument loads from %s), overwrites the library.", DEFAULT_SAVE_PATH)
        );
        IO.println("Exit: Quit the program.");
    }

    /**
     * Handles a {@code String[]} of arbitrary length and maps it
     * to a known command. If the command is not able to be found,
     * prints "Unknown command." to the terminal.
     * 
     * @apiNote This method is currently public for testing. It will be
     * set to private when the full dynamic user input system is implemented.
     * 
     * @param command an array of command arguments 
     */
    public void handleCommand(String... command) {
        // Use the first word as the main command
        switch (command[0]) {
            case "Add" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to add.");
                }

                for (int i = 1; i < command.length; i++) {
                    addGame(command[i]);
                }
            }
            case "Remove" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to remove.");
                }
                
                for (int i = 1; i < command.length; i++) {
                    removeGame(command[i]);
                }
            }
            case "Check" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to check for.");
                }

                for (int i = 1; i < command.length; i++) {
                    IO.println(
                        hasDuplicate(command[i]) ? 
                            String.format("%s found.", command[i]) : String.format("%s not found.", command[i])
                    );
                }
            }
            case "Find" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to find.");
                }

                int index;
                for (int i = 1; i < command.length; i++) {
                    IO.println(
                        (index = gameIndex(command[i])) != -1 ?
                            String.format("%s found at index %d.", command[i], index)
                                : String.format("%s not found.", command[i])
                    );
                }
            }
            case "Display" -> {
                switch (command.length) {
                    case 1 -> displayLibrary();
                    case 3 -> {
                        try {
                            int start = Integer.parseInt(command[1]);
                            int end = Integer.parseInt(command[2]);
                            displayLibrary(start, end);
                        } catch (NumberFormatException e) {
                            IO.println("Invalid index format, please provide integers.");
                        }
                    }
                    default -> IO.println("Unknown command");
                    // TODO: Remind user of commands
                }
            }
            case "Save" -> {
                switch (command.length) {
                    case 1 -> saveLibrary();
                    case 2 -> saveLibrary(command[1]);
                    default -> {
                        IO.println("Unknown command.");
                        // TODO: Remind user of commands
                    }
                }
            }
            case "Load" -> {
                switch (command.length) {
                    case 1 -> loadLibrary();
                    case 2 -> loadLibrary(command[1]);
                    default -> {
                        IO.println("Unknown command.");
                        // TODO: Remind user of commands
                    }
                }
            }
            case "Exit" -> {
                // TODO: Implement boolean to exit loop
            }
            default -> {
                IO.println("Unknown command.");
                // TODO: Remind user of commands
            }
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
