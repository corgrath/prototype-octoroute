package octoroute.action;

import octoroute.exceptions.OctorouteException;

public interface ActionBroker {

    public void sendRequest(RequestAction request) throws OctorouteException;

}
