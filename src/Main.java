public class Main {
    public static void main(String[] args) {
        // Testing all functionality
        GameLibrary library = new GameLibrary();

        library.addGame("Game1");
        library.addGame("Game3");
        library.addGame("Game7");
        library.addGame("Game5");
        library.addGame("Game6");
        library.addGame("Game8");
        library.addGame("Game2");
        library.addGame("Game1");
        library.addGame("game2");
        library.removeGame("game3");
        library.addGame(null);
        library.removeGame("Game4");
        library.removeGame(null);

        library.displayLibrary(1, 4);

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
        library.displayLibrary(3, 6);
        
        library.displayLibrary();

        library.saveLibrary();

        library.loadLibrary();
        library.displayLibrary();

        library.loadLibrary("/test/OtherLibrary.txt");
        library.displayLibrary();

        library.loadLibrary("/badtest/OtherLibrary.txt");
        library.displayLibrary();
    }
}
