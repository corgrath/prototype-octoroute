package octoroute.action;

import octoroute.exceptions.OctorouteException;
import octoroute.network.NetworkService;

/**
 * Broker that will try and act immediately without a queue.
 */

public class ImmediateActionBroker implements ActionBroker {

    private final NetworkService networkService;

    public ImmediateActionBroker(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public void sendRequest(RequestAction request) throws OctorouteException {
        this.networkService.execute(request);
    }

}
