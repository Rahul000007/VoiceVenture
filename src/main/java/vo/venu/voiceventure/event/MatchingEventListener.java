package vo.venu.voiceventure.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;
import vo.venu.voiceventure.service.UserService;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;

@Component
public class MatchingEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchHolder matchHolder;

    @EventListener
    public void onMatchAcceptanceEvent(MatchAcceptanceEvent event) {
        Long userId = event.getUserId();
        Long matchedUserId = event.getMatchedUserId();

        if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED) && matchHolder.getMatchAcceptanceEvent().get(event.getMatchedUserId()).getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED)){
            messagingTemplate.convertAndSendToUser(userService.getUsernameById(userId), "/queue/match-accepted",  matchedUserId);
            messagingTemplate.convertAndSendToUser(userService.getUsernameById(matchedUserId), "/queue/match-accepted", userId);

            matchHolder.getUsersInMatchingState().remove(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);

        }else if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.DECLINED)){
            messagingTemplate.convertAndSendToUser(userService.getUsernameById(userId), "/queue/match-decline", matchedUserId);

            matchHolder.getUsersInMatchingState().remove(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);

        }else if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.PENDING)){

        }
    }

    @EventListener
    public void onTest(Long event) {
        System.out.println("Long =>" +event);
    }

}
