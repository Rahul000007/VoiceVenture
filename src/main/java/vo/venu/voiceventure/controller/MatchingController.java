package vo.venu.voiceventure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.service.MatchingService;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    @Autowired
    private  MatchingService matchingService;

    @PostMapping("/start")
    public void startMatching(@RequestBody Long userId) {
         matchingService.startMatching(userId);
    }

    @PostMapping("/stop")
    public void stopMatching(@RequestBody Long userId) {
         matchingService.stopMatching(userId);
    }

}

