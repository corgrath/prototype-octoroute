package octoroute.action;

import octoroute.network.HTTPRequestMethod;

import java.util.Map;

public interface RequestAction {

    HTTPRequestMethod getMethod();

    Map<String, String> getHeaders();

    String getURL();

    String requestData();

}

