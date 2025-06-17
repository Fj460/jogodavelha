package com.example.Agentes.IA;



import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final Map<String, Long> rooms = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @PostMapping("/create")
    public String createRoom() {
        String code;
        do {
            code = generateCode();
        } while (rooms.containsKey(code));

        rooms.put(code, System.currentTimeMillis());
        return code;
    }

    @GetMapping("/join/{code}")
    public boolean joinRoom(@PathVariable String code) {
        Long createdTime = rooms.get(code);
        if (createdTime == null) return false;

        // Expire code if it's older than 5 minutes
        long now = System.currentTimeMillis();
        if ((now - createdTime) > 5 * 60 * 1000) {
            rooms.remove(code);
            return false;
        }

        return true;
    }

    private String generateCode() {
        int length = 6;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
