package octoroute.secret;

import octoroute.exceptions.OctorouteException;
import octoroute.services.LogServiceInterface;
import octoroute.utils.FileUtil;
import octoroute.utils.JSONUtil;

import java.util.Map;

public class SecretsService {

    private final LogServiceInterface logService;
    private Map<String, Object> data;

    public SecretsService(LogServiceInterface logService) {
        this.logService = logService;
        this.data = null;
    }

    public void initiate(String file) throws OctorouteException {

        if (!FileUtil.exists(file)) {
            throw new OctorouteException("The file \"" + file + "\" does not seem to exist.");
        }

        this.logService.log("SecretsService", "Initializing secrets service with \"" + file + "\".");

        String json = FileUtil.readFileToString(file);

        this.data = JSONUtil.toObject(json, Map.class);

    }

    public String get(String key) throws OctorouteException {

        if (this.data == null) {
            throw new OctorouteException("Secrets is null. Has it been initialized?");
        }

        return (String) this.data.get(key);

    }

}
