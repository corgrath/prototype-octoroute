package octoroute.util;

/**
 * Represents an UUID (Universally Unique Identifier);
 */
public class UUID {

    private final String id;

    public UUID(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }

}
