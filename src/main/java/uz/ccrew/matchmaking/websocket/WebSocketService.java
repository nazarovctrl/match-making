package uz.ccrew.matchmaking.websocket;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.List;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final Map<String, WebSocketSession> sessions;

    public void sendMessage(List<String> users, String message) {
        users.forEach(user -> sendMessage(user, message));
    }

    @Async
    public void sendMessage(String user, String message) {
        WebSocketSession session = sessions.get(user);
        if (session == null) {
            log.warn("User {} did not receive a notification about the start of the match, Because user's session not found", user);
            return;
        }
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.warn("User {} did not receive a notification about the start of the match", user);
        }
    }
}