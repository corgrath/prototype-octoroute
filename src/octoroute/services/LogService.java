package octoroute.services;

import octoroute.utils.DateUtil;

import java.time.LocalDateTime;

public class LogService implements LogServiceInterface {

    @Override
    public void log(String source, String message) {
        LocalDateTime now = DateUtil.nowLocal();
        String created = DateUtil.toYYYYMMDDHHMMSS(now);

        System.out.println("[" + source + "] " + message);
    }

}