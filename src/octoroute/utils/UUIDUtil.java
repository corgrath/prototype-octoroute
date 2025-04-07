package octoroute.utils;

public abstract class UUIDUtil {

    public static UUID generate() {
        return new UUID(java.util.UUID.randomUUID().toString());
    }

}
