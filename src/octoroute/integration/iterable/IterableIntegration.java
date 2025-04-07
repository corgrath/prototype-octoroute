package octoroute.integration.iterable;

import octoroute.action.ActionBroker;
import octoroute.action.RequestAction;
import octoroute.exceptions.OctorouteException;
import octoroute.network.HTTPRequestMethod;
import octoroute.services.LogServiceInterface;
import octoroute.utils.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Iterable API Integration
 *
 * @see "https://api.iterable.com/api/docs"
 */

public class IterableIntegration {

    private final LogServiceInterface logService;
    private final ActionBroker actionBroker;
    private final IterableDataCenter iterableDataCenter;
    private final String apiKey;

    public IterableIntegration(
            LogServiceInterface logService,
            ActionBroker actionBroker,
            IterableDataCenter iterableDataCenter,
            String apiKey
    ) {
        this.logService = logService;
        this.actionBroker = actionBroker;
        this.iterableDataCenter = iterableDataCenter;
        this.apiKey = apiKey;
    }

    /**
     * Updates a user by a given user ID.
     *
     * @param userID   The User ID
     * @param userData The user data
     * @see "https://api.iterable.com/api/doccmd#users_updateUser"
     */
    public void updateUserByID(String userID, Map<String, Object> userData) throws OctorouteException {

        RequestAction scheduledRequest = new RequestAction() {
            @Override
            public HTTPRequestMethod getMethod() {
                return HTTPRequestMethod.POST;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Api-Key", apiKey);
                return headers;
            }

            @Override
            public String getURL() {
                return createURL("/api/users/update");
            }

            @Override
            public String requestData() {
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", userID);
                payload.put("dataFields", userData);
                return JSONUtil.toStringPretty(payload);
            }

        };

        this.actionBroker.sendRequest(scheduledRequest);

    }

    /**
     * Sends a tracking event connected to the given user ID.
     *
     * @param userID    The User ID
     * @param eventName The event name
     * @param eventData The event data
     * @see "https://api.iterable.com/api/docs#events_track"
     */
    public void trackEvent(String userID, String eventName, Map<String, Object> eventData) throws OctorouteException {

        RequestAction scheduledRequest = new RequestAction() {
            @Override
            public HTTPRequestMethod getMethod() {
                return HTTPRequestMethod.POST;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Api-Key", apiKey);
                return headers;
            }

            @Override
            public String getURL() {
                return createURL("/api/events/track");
            }

            @Override
            public String requestData() {
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", userID);
                payload.put("eventName", eventName);
                payload.put("dataFields", eventData);
                return JSONUtil.toStringPretty(payload);
            }

        };

        this.actionBroker.sendRequest(scheduledRequest);

    }

    private String createURL(String path) {
        return "https://api." + (this.iterableDataCenter == IterableDataCenter.EU ? "eu." : "") + "iterable.com" + path;
    }

}