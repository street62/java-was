package was.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session {
    private static final List<String> sessionIdList = new ArrayList<>();

    public static String createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessionIdList.add(sessionId);
        return sessionId;
    }

    public static boolean containsSessionId(String sessionId) {
        return sessionIdList.stream()
                .anyMatch(id -> id.equals(sessionId));
    }
}
