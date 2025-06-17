package com.example.Agentes.IA;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LobbyController {

    @GetMapping("/lobbyzzz")
    public String showLobby() {
        return "lobby"; // This should resolve to src/main/resources/templates/lobby.html
    }
}
