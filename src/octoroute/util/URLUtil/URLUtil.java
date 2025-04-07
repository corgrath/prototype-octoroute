package octoroute.util.URLUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLUtil {
    public static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
