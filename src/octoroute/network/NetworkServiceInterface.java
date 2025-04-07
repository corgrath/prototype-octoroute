package octoroute.network;

import octoroute.action.RequestAction;
import octoroute.exceptions.OctorouteException;

public interface NetworkServiceInterface {

    void execute(RequestAction request) throws OctorouteException;

}