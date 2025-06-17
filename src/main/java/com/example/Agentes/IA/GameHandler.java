package com.example.Agentes.IA;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
public class GameHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> sessionRoomMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Wait for message to assign to room
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message msg = mapper.readValue(message.getPayload(), Message.class);
        String roomCode = msg.getRoomCode();
        Room room = RoomManager.getOrCreateRoom(roomCode);

        if (!sessionRoomMap.containsKey(session.getId())) {
            if (!room.addPlayer(session)) {
                session.sendMessage(new TextMessage("Room full"));
                return;
            }
            sessionRoomMap.put(session.getId(), roomCode);
        }

        if ("reset".equals(msg.getType())) {
            room.getGame().reset();
        } else if ("move".equals(msg.getType())) {
            String player = room.getSymbolFor(session.getId());
            room.getGame().makeMove(player, msg.getX(), msg.getY());
        }

        // Broadcast to all in room
        String payload = mapper.writeValueAsString(room.getGame());
        for (WebSocketSession s : room.getPlayers().values()) {
            if (s.isOpen())
                s.sendMessage(new TextMessage(payload));
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
