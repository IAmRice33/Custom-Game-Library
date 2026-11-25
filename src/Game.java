import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * 
 * Game
 * 
 * A game object that holds a game's name
 * and its tags.
 * 
 * @author Kyle Yang
 * 
 * @version v1.1.0
 * 
 * @since 1.1
 */
public class Game {
    private String name;
    private ArrayList<String> tags;

    public Game(String name, String... tags) {
        this.name = name;
        this.tags = new ArrayList<>();
        Collections.addAll(this.tags, tags);
    }

    /**
     * Gets the name of the game object.
     * 
     * @return the game's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the tags associated with the game object.
     * 
     * @return the game's tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    @Override 
    public boolean equals(Object o) {
        return o instanceof Game g
            && name.equals(g.name)
            && tags.equals(g.tags);
    }

    @Override
    public String toString() {
        String str = "";
        str += name;
        for (int i = 0; i < tags.size(); i++) {
            str += "," + tags.get(i);
        }

        return str;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.tags);
        return hash;
    }
}
