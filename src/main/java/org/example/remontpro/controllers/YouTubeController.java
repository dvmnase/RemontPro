package org.example.remontpro.controllers;

import org.example.remontpro.services.YouTubeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    private final YouTubeService youTubeService;

    public YouTubeController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/video")
    public String getVideo() {
        return youTubeService.getVideoDetails();  // Без параметров
    }
}