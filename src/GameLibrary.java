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
import java.util.Arrays;

/**
 * 
 * GameLibrary
 * 
 * Manages a game library for games.
 * 
 * @author Kyle Yang
 * 
 * @version v1.2.0
 * 
 * @since 1.0
 */
public class GameLibrary {
    // Use ArrayList<String> to dynamically store games.
    private final ArrayList<Game> library;
    // This is the default path to save the data to.
    private static final String DEFAULT_SAVE_PATH = "/data/GameLibrary.txt";
    private boolean running;

    public GameLibrary() {
        library = new ArrayList<>();
        running = false;
    }

    /**
     * Adds a game to the library.
     * Prevents duplicate games and prints result to terminal.
     * Rejects null values.
     * 
     * @param game the game to add
     */
    private void addGame(Game game) {
        if (game == null) {
            IO.println("Game provided to add is null.");
            return;
        }

        if (hasDuplicate(game)) {
            IO.println(String.format("Failed to add %s: Game already exists.", game));
        } else {
            library.add(game);
            IO.println(String.format("Successfully added %s.", game));
        }
    }

    /**
     * Removes the game from the library matching the requested game.
     * Prints the result to the terminal and null values are rejected.
     * 
     * @param game the game to remove
     */
    private void removeGame(Game game) {
        if (game == null) {
            IO.println("Game provided for removal is null.");
            return;
        }

        int index = gameIndex(game);

        if (index != -1) {
            library.remove(index - 1);
            IO.println(String.format("Successfully removed %s from the library.", game));
        } else {
            IO.println(String.format("%s was not found in the library.", game));
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
    private void displayLibrary(int start, int end) {
        // Check first to prevent throwing an exception on an empty library;
        if (library.isEmpty()) {
            IO.println("Game library is empty.");
            return;
        }

        if (start < 1) {
            IO.println("Library begins at index 1");
            return;
        }

        if (end < start) {
            IO.println("Display cannot end before start");
            return;
        }

        if (start >= library.size() || end > library.size()) {
            IO.println(String.format("Game library has size %d", library.size()));
            return;
        }

        // Create a separator on both ends to make output clear.
        // Format a title at the top, and use left and right justified widths to ensure spacing.
        IO.println(String.format("%-20s%s%20s", "-".repeat(19), "Game Library", "-".repeat(19)));

        // Print each game on a new line and number it based on position in library.
        // Use 1-index to align user input of start and end with the displayed numbers
        // next to the games. Start at start - 1 as number printed is i + 1.
        for (int i = start - 1; i < end; i++) {
            Game game = library.get(i);
            String toPrint = game.getName();
            ArrayList<String> tags = game.getTags();

            // Separate tags if they exist
            if (!tags.isEmpty()) {
                toPrint += " | Tags: ";
            }

            for (int j = 0; j < tags.size(); j++) {
                toPrint += tags.get(j);
                if (j != tags.size() - 1) {
                    toPrint += ", ";
                }
            } 
            IO.println(String.format("%d. %s", (i + 1), toPrint));
        }

        // Bottom separator
        IO.println(String.format("%52s", "-".repeat(52)));
    }

    /**
     * Displays the full contents of the game library.
     * 
     * @see #displayLibrary(int, int)
     */
    private void displayLibrary() {
        displayLibrary(1, library.size());
    }

    /**
     * Create a file at the supplied file path and save the game library's data 
     * in this file. This method exists if the user wants to use a custom file path.
     * 
     * @param filePath the file path to save the game library's data at
     */
    private void saveLibrary(String filePath) {
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
            for (Game game : library) {
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
    private void saveLibrary() {
        saveLibrary(DEFAULT_SAVE_PATH);
    }

    /**
     * Loads game library data from the supplied file path. 
     * This method exists if the user decides to load data from a custom file path.
     * 
     * @param filePath the file path to load data from
     */
    private void loadLibrary(String filePath) {
        // Empty the library to keep game libraries unique.
        library.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("../" + filePath))) {
            String gameString;
            while ((gameString = reader.readLine()) != null) {
                // Use addGame to uphold the game library's rules.
                Game game = parseGame(gameString);
                addGame(game);
            }
            IO.println("Load Complete.");
        } catch (IOException e) {
            IO.println("Could not load from file: " + filePath + ". File may not exist.");
        }
    }

    /**
     * Loads the game library's data from the default save file path
     * of {@value #DEFAULT_SAVE_PATH} from the root directory.
     * 
     * @see #loadLibrary(String)
     */
    private void loadLibrary() {
        loadLibrary(DEFAULT_SAVE_PATH);
    }

    public void manageLibrary() {
        running = true;

        IO.println("Starting management of the game library.");
        printCommands();
        while (running) {
            // Handle future command printing through handleCommand()
            // to prevent flooding terminal while managing the game library.
            handleCommand(handleUserInput());
        }
        IO.println();
    }

    /**
     * Checks if the requested game already exists in the game library.
     * Duplicate games are checked to prevent accidental replicating of games when 
     * the game library becomes large.
     * 
     * @param game the game to check for
     * @return true if the game exists in library, false otherwise
     */
    private boolean hasDuplicate(Game game) {
        for (Game libraryGame : library) {
            if (libraryGame.equals(game)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the index of the first occurance of the requested game
     * in the game library, or -1 if there is no such game.
     *  
     * @param game the game to find
     * @return the index of the first occurance of requested game, or -1
     * if the game is not in the library
     */
    private int gameIndex(Game game) {
        for (int i = 0; i < library.size(); i++) {
            // Compare both strings in lowercase to ignore case sensitivity.
            if (library.get(i).equals(game)) {
                // Return i + 1 as display is 1-indexed
                return i + 1;
            }
        }

        return -1;
    }

    /**
     * Gets the name of the game in the library at 
     * the requested index.
     * 
     * @param index the index to get
     * @return the name of the game at the index
     * 
     * @since 1.2
     */
    private String getName(int index) {
        if (index < 1) {
            IO.println("Library begins at index 1");
            return null;
        }

        if (index > library.size()) {
            IO.println("Library ends at index " + library.size());
            return null;
        }

        return library.get(index - 1).getName();
    }

    /**
     * Gets the tags of the game in the library at 
     * the requested index.
     * 
     * @param index the index to get
     * @return the tags of the game at the index
     * 
     * @since 1.2
     */
    private ArrayList<String> getTags(int index) {
        if (index < 1) {
            IO.println("Library begins at index 1");
            return null;
        }

        if (index > library.size()) {
            IO.println("Library ends at index " + library.size());
            return null;
        }

        return library.get(index - 1).getTags();
    }

    /**
     * Parses a given {@code String} and returns a new Game object.
     * 
     * @param gameString the {@code String} to be parsed
     * @return a new Game object
     * 
     * @since 1.1
     */
    private Game parseGame(String gameString) {
        String[] separatedString = gameString.split(",");
        if (separatedString.length > 1) {
            return new Game(separatedString[0], Arrays.copyOfRange(separatedString, 1, separatedString.length));
        }

        return new Game(separatedString[0]);
    }
    
    /**
     * Prints a list of game library commands and their arguments to the terminal.
     */
    private void printCommands() {
        IO.println("List of Available Commands");
        IO.println("Please keep all arguments separated by a single space and case sensitive.");
        IO.println("Games are written in the format: name,tag1,tag2,tag3,...");
        IO.println("Add {Game1 Game2 ...}: Adds listed games to the library");
        IO.println("Remove {Game1 Game2 ...}: Removes listed games from the library");
        IO.println("Name {index}: Gets the name of the game at the index");
        IO.println("Tags {index}: Gets the tags of the game at the index");
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
     * Takes space seperated user input from the terminal and returns
     * each word as an element in a single String array.
     * 
     * @return array of Strings of space-separated terminal input
     */
    private String[] handleUserInput() {
        return IO.readln().split(" ");
    }

    /**
     * Handles a {@code String[]} of arbitrary length and maps it
     * to a known command. If the command is not able to be found,
     * prints "Unknown command." to the terminal.
     * The first element is the command, and all other elements
     * are treated as arguments.
     * 
     * @param command an array of command arguments 
     */
    private void handleCommand(String... command) {
        // Prevent inconsistent functionality due to multiple consecutive spaces
        for (String argument : command) {
            if (argument.equals("")) {
                IO.println("Please keep spacing between arguments to one space");
                return;
            }
        }

        // Use the first word as the main command
        switch (command[0]) {
            case "Add" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to add.");
                }

                for (int i = 1; i < command.length; i++) {
                    addGame(parseGame(command[i]));
                }
            }
            case "Remove" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to remove.");
                }
                
                for (int i = 1; i < command.length; i++) {
                    removeGame(parseGame(command[i]));
                }
            }
            case "Name" -> {
                if (command.length == 1) {
                    IO.println("Please give an index to check.");
                    break;
                }
                if (command.length > 2) {
                    IO.println("Only one index can be checked at once");
                    break;
                }
                try {
                    String name = getName(Integer.parseInt(command[1]));
                    if (name == null) {
                        break;
                    }
                    IO.println(name);
                } catch (NumberFormatException e) {
                    IO.println("Please give a valid integer index");
                }
            }
            case "Tags" -> {
                if (command.length == 1) {
                    IO.println("Please give an index to check.");
                    break;
                }
                if (command.length > 2) {
                    IO.println("Only one index can be checked at once");
                    break;
                }
                try {
                    ArrayList<String> tags = getTags(Integer.parseInt(command[1]));
                    if (tags.isEmpty()) {
                        IO.println("No tags");
                        break;
                    }
                    String toPrint = "";
                    for (int i = 0; i < tags.size(); i++) {
                        toPrint += tags.get(i);
                        if (i != tags.size() - 1) {
                            toPrint += ", ";
                        }
                    }
                    IO.println(toPrint);
                } catch (NumberFormatException e) {
                    IO.println("Please give a valid integer index");
                }
            }
            case "Check" -> {
                if (command.length == 1) {
                    IO.println("Please specify games to check for.");
                }

                for (int i = 1; i < command.length; i++) {
                    IO.println(
                        hasDuplicate(parseGame(command[i])) ? 
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
                        (index = gameIndex(parseGame(command[i]))) != -1 ?
                            String.format("%s found at index %d.", command[i], index)
                                : String.format("%s not found.", command[i])
                    );
                }
            }
            case "Display" -> {
                switch (command.length) {
                    case 1 -> displayLibrary();
                    case 2 -> {
                        try {
                            int index = Integer.parseInt(command[1]);
                            displayLibrary(index, index);
                        } catch (NumberFormatException e) {
                            IO.println("Invalid index format, please provide integers.");
                        }
                    }
                    case 3 -> {
                        try {
                            int start = Integer.parseInt(command[1]);
                            int end = Integer.parseInt(command[2]);
                            displayLibrary(start, end);
                        } catch (NumberFormatException e) {
                            IO.println("Invalid index format, please provide integers.");
                        }
                    }
                    default -> {
                        IO.println("Unknown command");
                        printCommands();
                    }
                }
            }
            case "Save" -> {
                switch (command.length) {
                    case 1 -> saveLibrary();
                    case 2 -> saveLibrary(command[1]);
                    default -> {
                        IO.println("Unknown command.");
                        printCommands();
                    }
                }
            }
            case "Load" -> {
                switch (command.length) {
                    case 1 -> loadLibrary();
                    case 2 -> loadLibrary(command[1]);
                    default -> {
                        IO.println("Unknown command.");
                        printCommands();
                    }
                }
            }
            case "Exit" -> {
                running = false;
            }
            default -> {
                IO.println("Unknown command.");
                printCommands();
            }
        }
    }

    @Override 
    public String toString() {
        String gameList = "";

        for (Game game : library) {
            gameList += game + " ";
        }

        return gameList;
    }
}
