package vo.venu.voiceventure.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class MatchingEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final MatchHolder matchHolder;
    private final MatchingService matchingService;


    @EventListener
    public void onMatchAcceptanceEvent(MatchAcceptanceEvent event) {
        String userId = event.getUserId();
        String matchedUserId = event.getMatchedUserId();

        if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED) && matchHolder.getMatchAcceptanceEvent().get(event.getMatchedUserId()).getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED)) {
            boolean isUserCaller = matchHolder.getIsCaller().get(userId);
            boolean isMatchedUserCaller = matchHolder.getIsCaller().get(matchedUserId);

            messagingTemplate.convertAndSendToUser(userService.getUserNameByUserId(userId), "/queue/match-accepted", isUserCaller);
            messagingTemplate.convertAndSendToUser(userService.getUserNameByUserId(matchedUserId), "/queue/match-accepted", isMatchedUserCaller);

            matchHolder.getUsersInMatchingState().remove(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);

        } else if (event.getAcceptanceStatus().equals(MatchAcceptanceStatus.DECLINED)) {
            messagingTemplate.convertAndSendToUser(userService.getUserNameByUserId(userId), "/queue/match-decline", matchedUserId);

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
        String userId = event.getUserId();
        matchHolder.getActiveUsers().add(userId);
        log.info("UserId {} started matching", userId);
        log.info("Total Active users: {}", matchHolder.getActiveUsers().size());
        if (matchHolder.getActiveUsers().size() % 2 == 0) {
            matchingService.triggerMatching();
        }
    }

}
