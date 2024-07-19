package uz.ccrew.matchmaking.exp;

import org.springframework.http.HttpStatus;

public class ServerUnavailableException extends BasicException {
    public ServerUnavailableException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
