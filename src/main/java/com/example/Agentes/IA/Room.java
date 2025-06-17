package com.example.Agentes.IA;


import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private WebSocketSession playerX;
    private WebSocketSession playerO;
    private final GameSession game = new GameSession();
    private final long creationTime = System.currentTimeMillis();

    public boolean addPlayer(WebSocketSession session) {
        synchronized (this) {
            if (playerX == null) {
                playerX = session;
                return true;
            } else if (playerO == null) {
                playerO = session;
                return true;
            }
            return false;
        }
    }

    public void removePlayer(WebSocketSession session) {
        synchronized (this) {
            if (session.equals(playerX)) {
                playerX = null;
            } else if (session.equals(playerO)) {
                playerO = null;
            }
        }
    }

    public void makeMove(WebSocketSession session, int x, int y) {
        synchronized (game) {
            String symbol = getPlayerSymbol(session);
            if (symbol != null) {
                game.makeMove(symbol, x, y);
            }
        }
    }

    public void resetGame() {
        synchronized (game) {
            game.reset();
        }
    }

    public List<WebSocketSession> getPlayers() {
        List<WebSocketSession> list = new ArrayList<>();
        if (playerX != null) list.add(playerX);
        if (playerO != null) list.add(playerO);
        return list;
    }

    public String getPlayerSymbol(WebSocketSession session) {
        if (session.equals(playerX)) return "X";
        if (session.equals(playerO)) return "O";
        return null;
    }

    public GameSession getGame() {
        return game;
    }

    public long getCreationTime() {
        return creationTime;
    }
}


