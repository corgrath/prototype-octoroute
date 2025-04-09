package octoroute.integration.google.mp;

import octoroute.action.ActionBroker;
import octoroute.action.RequestAction;
import octoroute.exceptions.OctorouteException;
import octoroute.network.HTTPRequestMethod;
import octoroute.services.LogServiceInterface;
import octoroute.util.URLUtil.URLUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Google Measurement Protocol
 *
 * @see "https://developers.google.com/analytics/devguides/collection/protocol/ga4/reference?client_type=firebase"
 */

public class MeasurementProtocolIntegration {

    private final LogServiceInterface logService;
    private final ActionBroker actionBroker;
    private final String measurementID;
    private final String secret;

    public MeasurementProtocolIntegration(
            LogServiceInterface logService,
            ActionBroker actionBroker,
            String measurementID,
            String secret
    ) {
        this.logService = logService;
        this.actionBroker = actionBroker;
        this.measurementID = measurementID;
        this.secret = secret;
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
                return "https://www.google-analytics.com/mp/collect?api_secret=" + URLUtil.encode(secret) + "&measurement_id=" + URLUtil.encode(measurementID);
            }

            @Override
            public String requestData() {

//                Map<>
//
//
//                Map<String, Object> payload = new HashMap<>();
//                return JSONUtil.toStringPretty(payload);

                return String.format("""
                        {
                            "client_id": "123456789.987654321",
                            "events": [
                              {
                                "name": "%s",
                                "params": {
                                  "currency": "USD",
                                  "value": 9.99
                                }
                              }
                            ]
                          }
                        """, eventName);
            }

        };

        this.actionBroker.sendRequest(scheduledRequest);

    }

}