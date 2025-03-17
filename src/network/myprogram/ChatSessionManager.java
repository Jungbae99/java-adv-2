package network.myprogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatSessionManager {

    private final List<ChatSession> sessions = new ArrayList<>();

    public synchronized List<ChatSession> getSessions() {
        return Collections.unmodifiableList(sessions);
    };

    public synchronized void add(ChatSession session) {
        sessions.add(session);
    }

    public synchronized void remove(ChatSession session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (ChatSession session : sessions) {
            session.close();
        }
        sessions.clear();
    }
}
