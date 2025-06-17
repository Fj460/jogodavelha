package com.example.Agentes.IA;


import org.springframework.web.socket.WebSocketSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private final Map<String, String> playerSymbols = new ConcurrentHashMap<>();
    private final GameSession game = new GameSession();
    private final long creationTime = System.currentTimeMillis();
    public long getCreationTime() { return creationTime; }

    

    public GameSession getGame() {
        return game;
    }

    public boolean addPlayer(WebSocketSession session) {
        if (players.size() >= 2) return false;
        String symbol = players.isEmpty() ? "X" : "O";
        players.put(session.getId(), session);
        playerSymbols.put(session.getId(), symbol);
        return true;
    }

    public void removePlayer(WebSocketSession session) {
        players.remove(session.getId());
        playerSymbols.remove(session.getId());
    }

    public Map<String, WebSocketSession> getPlayers() {
        return players;
    }

    public String getSymbolFor(String sessionId) {
        return playerSymbols.get(sessionId);
    }
}

