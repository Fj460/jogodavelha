package com.example.Agentes.IA;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class GameHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> sessionRoomMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Do nothing until message arrives
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> data = mapper.readValue(message.getPayload(), Map.class);
        String type = (String) data.get("type");
        String roomCode = (String) data.get("roomCode");
        Room room = RoomManager.getOrCreateRoom(roomCode);

        if (!sessionRoomMap.containsKey(session.getId())) {
            if (!room.addPlayer(session)) {
                session.sendMessage(new TextMessage("{\"error\": \"Room full\"}"));
                return;
            }
            sessionRoomMap.put(session.getId(), roomCode);
        }

        if ("move".equals(type)) {
            int x = (int) data.get("x");
            int y = (int) data.get("y");
            room.makeMove(session, x, y);
        } else if ("reset".equals(type)) {
            room.resetGame();
        }

        // Prepare response
        GameSession game = room.getGame();
        Map<String, Object> response = new HashMap<>();
        response.put("board", game.getBoard());
        response.put("currentPlayer", game.getCurrentPlayer());
        response.put("status", game.getStatus());

        for (WebSocketSession player : room.getPlayers()) {
            if (player.isOpen()) {
                response.put("yourSymbol", room.getPlayerSymbol(player));
                String json = mapper.writeValueAsString(response);
                player.sendMessage(new TextMessage(json));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomCode = sessionRoomMap.remove(session.getId());
        if (roomCode != null) {
            Room room = RoomManager.getRoom(roomCode);
            if (room != null) {
                room.removePlayer(session);
                if (room.getPlayers().isEmpty()) {
                    RoomManager.removeRoom(roomCode);
                }
            }
        }
    }
}
