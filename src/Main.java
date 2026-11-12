public class Main {
    public static void main(String[] args) {
        // Testing all functionality
        GameLibrary library = new GameLibrary();

        // library's ArrayList size increases by 1 per call, desireable
        library.addGame("Game1");
        library.addGame("Game2");
        library.addGame("Game3");
    }
}
