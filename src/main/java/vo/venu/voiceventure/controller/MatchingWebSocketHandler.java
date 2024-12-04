package vo.venu.voiceventure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.service.MatchingService;

@Slf4j
@Controller
public class MatchingWebSocketHandler {

    @Autowired
    private MatchingService matchingService;

    @MessageMapping("/matchAcceptance")
    @SendTo("/topic/matches")
    public void matchAcceptance(MatchAcceptanceEvent matchAcceptanceEvent) {
        log.info(String.valueOf(matchAcceptanceEvent));
        matchingService.handleMatchAcceptance(matchAcceptanceEvent);
    }
}
