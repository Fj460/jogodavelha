package com.example.Agentes.IA;


import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RoomManager {
    private static final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRoomCode() {
        String code;
        do {
            code = new Random().ints(6, 0, CHARACTERS.length())
                    .mapToObj(i -> String.valueOf(CHARACTERS.charAt(i)))
                    .collect(Collectors.joining());
        } while (rooms.containsKey(code));
        return code;
    }

    public static Room getOrCreateRoom(String roomCode) {
        return rooms.computeIfAbsent(roomCode, code -> new Room());
    }

    public static boolean roomExists(String roomCode) {
        return rooms.containsKey(roomCode);
    }

    public static Room getRoom(String roomCode) {
        return rooms.get(roomCode);
    }

    public static void removeRoom(String roomCode) {
        rooms.remove(roomCode);
    }
    static {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            rooms.entrySet().removeIf(entry ->
                    entry.getValue().getPlayers().isEmpty() &&
                            now - entry.getValue().getCreationTime() > 5 * 60 * 1000 // 5 min
            );
        }, 1, 1, TimeUnit.MINUTES);
    }

}

