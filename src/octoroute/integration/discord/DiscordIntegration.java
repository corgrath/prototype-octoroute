package octoroute.integration.discord;

import octoroute.action.ActionBroker;
import octoroute.action.RequestAction;
import octoroute.exceptions.OctorouteException;
import octoroute.network.HTTPRequestMethod;
import octoroute.services.LogServiceInterface;
import octoroute.util.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class DiscordIntegration {

    private final LogServiceInterface logService;
    private final ActionBroker actionBroker;

    public DiscordIntegration(
            LogServiceInterface logService,
            ActionBroker actionBroker
    ) {
        this.logService = logService;
        this.actionBroker = actionBroker;
    }

    public void sendSimpleMessage(String channelID, String token, String message) throws OctorouteException {
        String url = "https://discordapp.com/api/webhooks/" + channelID + "/" + token;
        sendSimpleMessage(url, message);
    }

    public void sendSimpleMessage(String url, String message) throws OctorouteException {

        RequestAction scheduledRequest = new RequestAction() {

            @Override
            public HTTPRequestMethod getMethod() {
                return HTTPRequestMethod.POST;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getURL() {
                return url;
            }

            @Override
            public String requestData() {

                Map<String, Object> payload = new HashMap<>();
                payload.put("content", message);
                return JSONUtil.toStringPretty(payload);

            }

        };

        this.actionBroker.sendRequest(scheduledRequest);

    }

}

