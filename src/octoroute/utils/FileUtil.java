package octoroute.utils;

import octoroute.exceptions.OctorouteException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class FileUtil {

    public static boolean exists(String file) {
        return new File(file).exists();
    }

    public static String readFileToString(String file) throws OctorouteException {
        try {
            return FileUtils.readFileToString(new File(file), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new OctorouteException(exception);
        }
    }

}
