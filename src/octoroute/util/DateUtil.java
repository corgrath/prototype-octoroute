package octoroute.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @see "https://docs.oracle.com/javase/tutorial/datetime/iso/overview.html"
 */

public abstract class DateUtil {

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now();
    }

    public static String toYYYYMMDDHHMMSS(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

}
