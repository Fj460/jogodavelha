package com.example.Agentes.IA;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToLobby() {
        return "redirect:/lobby";
    }

    @GetMapping("/lobby")
    public String showLobby() {
        return "lobby";
    }

    @GetMapping("/game")
    public String showGame() {
        return "game";
    }
}
