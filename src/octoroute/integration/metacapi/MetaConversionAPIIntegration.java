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
 * @see "Payload Helper - https://developers.facebook.com/docs/marketing-api/conversions-api/payload-helper"
 */

public class MetaConversionAPIIntegration {

    private final LogServiceInterface logService;
    private final ActionBroker actionBroker;
    private final String pixelID;
    private final String accessToken;

    public MetaConversionAPIIntegration(
            LogServiceInterface logService,
            ActionBroker actionBroker,
            String pixelID,
            String accessToken
    ) {
        this.logService = logService;
        this.actionBroker = actionBroker;
        this.pixelID = pixelID;
        this.accessToken = accessToken;
    }

    public void sendEvent(String eventName) throws OctorouteException {

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
                return "https://graph.facebook.com/v22.0/" + pixelID + "/events?access_token=" + URLUtil.encode(accessToken);
            }

            @Override
            public String requestData() {

//                Map<>
//
//
//                Map<String, Object> payload = new HashMap<>();
//                return JSONUtil.toStringPretty(payload);

                return String.format("""
                        data=[
                               {
                          "event_name": "%s",
                          "event_time": %d,
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
                          "action_source": "website"
                        }
                        ]
                        """, eventName, (System.currentTimeMillis() / 1000));
            }

        };

        this.actionBroker.sendRequest(scheduledRequest);

    }

}