package vo.venu.voiceventure.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.dto.StartMatchingEvent;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;
import vo.venu.voiceventure.service.MatchingService;
import vo.venu.voiceventure.service.UserService;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;

@Component
@Slf4j
public class MatchingEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchHolder matchHolder;

    @Autowired
    private MatchingService matchingService;


    @EventListener
    public void onMatchAcceptanceEvent(MatchAcceptanceEvent event) {
        Long userId = event.getUserId();
        Long matchedUserId = event.getMatchedUserId();

        if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED) && matchHolder.getMatchAcceptanceEvent().get(event.getMatchedUserId()).getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED)) {
            boolean isUserCaller = matchHolder.getIsCaller().get(userId);
            boolean isMatchedUserCaller = matchHolder.getIsCaller().get(matchedUserId);

            messagingTemplate.convertAndSendToUser(userService.getUsernameById(userId), "/queue/match-accepted", isUserCaller);
            messagingTemplate.convertAndSendToUser(userService.getUsernameById(matchedUserId), "/queue/match-accepted", isMatchedUserCaller);

            matchHolder.getUsersInMatchingState().remove(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);

        } else if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.DECLINED)) {
            messagingTemplate.convertAndSendToUser(userService.getUsernameById(userId), "/queue/match-decline", matchedUserId);

            matchHolder.getUsersInMatchingState().remove(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);

        } else if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.PENDING)) {
            log.warn("PENDING");
        }
    }

    @EventListener
    public void onUserStartMatching(StartMatchingEvent event) {
        Long userId = event.getUserId();
        matchHolder.getActiveUsers().add(userId);
        log.info("User {} started matching. Active users: {}", userId, matchHolder.getActiveUsers());
        if (matchHolder.getActiveUsers().size() % 2 == 0) {
            matchingService.triggerMatching();
        }
    }

}
