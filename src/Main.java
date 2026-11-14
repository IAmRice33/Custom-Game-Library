public class Main {
    public static void main(String[] args) {
        // Testing all functionality
        GameLibrary library = new GameLibrary();

        library.addGame("Game1");
        library.addGame("Game3");
        library.addGame("Game2");
        library.addGame("Game1");
        library.addGame("game2");
        library.removeGame("Game3");
        library.addGame(null);
        library.removeGame("Game4");

        IO.println(library);
    }
}
