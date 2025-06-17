package com.example.Agentes.IA;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
    @GetMapping("/zzz")
    public String home() {
        return "game";
    }
    @GetMapping("/gamez")
    public String gamePage() {
        return "game";
    }

}

