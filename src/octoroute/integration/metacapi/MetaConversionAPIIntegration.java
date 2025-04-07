package octoroute.integration.metacapi;

import octoroute.action.ActionBroker;
import octoroute.action.RequestAction;
import octoroute.exceptions.OctorouteException;
import octoroute.network.HTTPRequestMethod;
import octoroute.services.LogServiceInterface;
import octoroute.util.URLUtil.URLUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Meta Conversions API
 *
 * @see "https://developers.facebook.com/docs/marketing-api/conversions-api/"
 */

public class MetaConversionAPIIntegration {

    private final LogServiceInterface logService;
    private final ActionBroker actionBroker;
    private final String pixelID;

    public MetaConversionAPIIntegration(
            LogServiceInterface logService,
            ActionBroker actionBroker,
            String pixelID
    ) {
        this.logService = logService;
        this.actionBroker = actionBroker;
        this.pixelID = pixelID;
    }

    public void sendEvent(String eventName) throws OctorouteException {

        String accessToken = "sandra123";

        RequestAction scheduledRequest = new RequestAction() {

            @Override
            public HTTPRequestMethod getMethod() {
                return HTTPRequestMethod.POST;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
//                headers.put("Api-Key", apiKey);
                return headers;
            }

            @Override
            public String getURL() {
                return "https://graph.facebook.com/v21.0/" + pixelID + "/events?access_token=" + URLUtil.encode(accessToken);
            }

            @Override
            public String requestData() {

//                Map<>
//
//
//                Map<String, Object> payload = new HashMap<>();
//                return JSONUtil.toStringPretty(payload);

                return """
                        data=[
                               {
                          "event_name": "Purchase",
                          "event_time": 1674000041,
                          "user_data": {
                            "em": [
                              "309a0a5c3e211326ae75ca18196d301a9bdbd1a882a4d2569511033da23f0abd"
                            ],
                            "ph": [
                              "254aa248acb47dd654ca3ea53f48c2c26d641d23d7e2e93a1ec56258df7674c4",
                              "6f4fcb9deaeadc8f9746ae76d97ce1239e98b404efe5da3ee0b7149740f89ad6"
                            ]
                          },
                          "custom_data": {
                            "currency": "usd",
                            "value": 123.45,
                            "contents": [{
                              "id": "product123",
                              "quantity": 1
                            }]
                          },
                          "action_source": "physical_store"
                        }
                        ]
                        """;
            }

        };

        this.actionBroker.sendRequest(scheduledRequest);

    }

}