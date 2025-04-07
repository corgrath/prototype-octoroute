package octoroute.exceptions;

import java.util.HashMap;
import java.util.Map;

public class OctorouteException extends Exception {

    private final Map<String, String> metadata;

    public OctorouteException(Throwable throwable) {
        this(throwable.getMessage());
    }

    public OctorouteException(Throwable throwable, Map<String, String> metadata) {
        this(throwable.getMessage(), metadata);
    }

    public OctorouteException(String message) {
        this(message, new HashMap<>());
    }

    public OctorouteException(OctorouteException exception) {
        this(exception.getMessage(), exception.getMetadata());
    }

    public OctorouteException(OctorouteException exception, Map<String, String> metadata) {
        this(exception.getMessage(), metadata);
    }

    public OctorouteException(String message, Map<String, String> metadata) {
        super(message);
        this.metadata = new HashMap<>(metadata);
    }

    public void addMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }
}
