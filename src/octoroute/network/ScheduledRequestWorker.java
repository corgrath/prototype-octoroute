package octoroute.network;

import octoroute.action.RequestAction;
import octoroute.services.LogService;

public class ScheduledRequestWorker implements Runnable {

    private final LogService logService;
    private final ScheduledRequestQueue queue;
    private final NetworkService networkService;

    public ScheduledRequestWorker(LogService logService, ScheduledRequestQueue queue, NetworkService networkService) {
        this.logService = logService;
        this.queue = queue;
        this.networkService = networkService;
    }

    @Override
    public void run() {
        this.logService.log("ScheduledRequestWorker", "Starting.");
        while (true) {
            try {
                RequestAction request = this.queue.pop();
                this.logService.log(ScheduledRequestWorker.class.getSimpleName(), "Popped " + request + " from queue.");
                this.networkService.execute(request);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Allow clean shutdown
            } catch (Exception e) {
                e.printStackTrace(); // Or send to DLQ
            }
        }
    }
}
