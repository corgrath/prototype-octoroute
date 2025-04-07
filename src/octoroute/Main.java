package octoroute;

import octoroute.action.ImmediateActionBroker;
import octoroute.exceptions.OctorouteException;
import octoroute.integration.iterable.IterableDataCenter;
import octoroute.integration.iterable.IterableIntegration;
import octoroute.integration.metacapi.MetaConversionAPIIntegration;
import octoroute.network.NetworkService;
import octoroute.network.ScheduledRequestQueue;
import octoroute.network.ScheduledRequestWorker;
import octoroute.secret.SecretsService;
import octoroute.services.LogService;
import octoroute.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws OctorouteException {

        // Initialize services
        LogService logService = new LogService();
        SecretsService secretsService = new SecretsService(logService);
        NetworkService networkService = new NetworkService(logService);
        ScheduledRequestQueue scheduledRequestQueue = new ScheduledRequestQueue(logService);

        // Initialize workers
        ScheduledRequestWorker worker = new ScheduledRequestWorker(
                logService,
                scheduledRequestQueue,
                networkService
        );
        Thread workerThread = new Thread(worker);
        workerThread.start();

        // Brokers
        ImmediateActionBroker immediateActionBroker = new ImmediateActionBroker(networkService);

        // Initialize secrets
        secretsService.initiate("secrets.json");
        String iterableAPIKey = secretsService.get("iterable.apikey");
        String pixelID = secretsService.get("meta.pixelid");

        IterableIntegration iterableIntegration = new IterableIntegration(
                logService,
                immediateActionBroker,
                IterableDataCenter.US,
                iterableAPIKey
        );

        Map<String, Object> userData = new HashMap<>();
        userData.put("pet", "a dog called sandy " + DateUtil.toYYYYMMDDHHMMSS(DateUtil.nowLocal()));
        iterableIntegration.updateUserByID("test@test.com", userData);

        Map<String, Object> eventData = new HashMap<>();
        iterableIntegration.trackEvent("test@test.com", "Debug.Event", eventData);

        MetaConversionAPIIntegration metaConversionAPIIntegration = new MetaConversionAPIIntegration(
                logService,
                immediateActionBroker,
                pixelID
        );

        metaConversionAPIIntegration.sendEvent("Donate");

    }

}