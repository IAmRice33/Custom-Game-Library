public class Main {
    public static void main(String[] args) {
        // Testing all functionality
        GameLibrary library = new GameLibrary();

        library.addGame("Game1");
        library.addGame("Game3");
        library.addGame("Game7");
        library.addGame("Game5");
        library.handleCommand("Add", "Game6");
        library.handleCommand(new String[]{"Add", "Game8", "Game2", "Game1", "game2"});
        library.removeGame("game3");
        library.addGame(null);
        library.handleCommand("Remove", "Game4", null);
        library.handleCommand("Remove", "Game7");

        library.handleCommand("Display", "1", "4");
        library.handleCommand("Display", "2");
        library.handleCommand("Display", "2", null);
        library.handleCommand("Display", "ba", "4");

        // Test all exceptions
        try {
            library.displayLibrary(-1, 3);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        try {
            library.displayLibrary(0, 3);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        try {
            library.displayLibrary(3, 3);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        try {
            library.displayLibrary(4, 3);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        try {
            library.displayLibrary(3, 0);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        try {
            library.displayLibrary(6, 7);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        try {
            library.displayLibrary(2, 7);
        } catch (IndexOutOfBoundsException e) {
            IO.println(e.getMessage());
        }
        library.displayLibrary(3, 5);
        
        library.displayLibrary();

        library.handleCommand("Save");

        library.handleCommand("Load");
        library.displayLibrary();

        library.handleCommand("Load", "/test/OtherLibrary.txt");
        library.displayLibrary();

        library.handleCommand("Load", "/badtest/OtherLibrary.txt");
        library.handleCommand("Load", "Anot.her Bad|test");
        library.displayLibrary();
        library.printCommands();
    }
}
