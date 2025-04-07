package octoroute.network;

import octoroute.action.RequestAction;
import octoroute.services.LogServiceInterface;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScheduledRequestQueue {

    private final BlockingQueue<RequestAction> queue;
    private final LogServiceInterface logService;

    public ScheduledRequestQueue(LogServiceInterface logService) {
        this.logService = logService;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void push(RequestAction request) {
        this.logService.log(ScheduledRequestQueue.class.getSimpleName(), "Adding " + request + " to the queue.");
        this.queue.add(request); // Does not block, throws an exception
    }

    public RequestAction pop() throws InterruptedException {
        return this.queue.take(); // Blocking dequeue
    }

}
