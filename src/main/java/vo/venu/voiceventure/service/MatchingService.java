package vo.venu.voiceventure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MatchingService {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private MatchHolder matchHolder;


    public void startMatching(Long userId) {
        matchHolder.getActiveUsers().add(userId);
        System.out.println("M-H => " + matchHolder.getActiveUsers());
        findMatch(userId);
    }


    public void findMatch(Long userId) {

        List<Long> eligibleUsers = new ArrayList<>();
        for (Long matchedUserId : matchHolder.getActiveUsers()) {
            if (!matchedUserId.equals(userId) &&
                    !matchHolder.getUsersInMatchingState().containsKey(userId) &&
                    !matchHolder.getUsersInMatchingState().containsKey(matchedUserId)) {
                eligibleUsers.add(matchedUserId);
            }
        }

        System.out.println("Eligible User => "+eligibleUsers);

        if (!eligibleUsers.isEmpty()) {

            Random random = new Random();

            int randomIndex = random.nextInt(eligibleUsers.size());
            Long matchedUserId = eligibleUsers.get(randomIndex);

            String userName = userService.getUsernameById(userId);
            String matchedUserName = userService.getUsernameById(matchedUserId);

            messagingTemplate.convertAndSendToUser(userName, "/queue/matching", matchedUserId);
            messagingTemplate.convertAndSendToUser(matchedUserName, "/queue/matching", userId);

            matchHolder.getUsersInMatchingState().put(userId, matchedUserId);
            matchHolder.getUsersInMatchingState().put(matchedUserId, userId);


            matchHolder.getActiveUsers().remove(userId);
            matchHolder.getActiveUsers().remove(matchedUserId);

            matchHolder.getMatchAcceptanceEvent().put(userId, new MatchAcceptanceEvent(userId, matchedUserId, MatchAcceptanceStatus.PENDING));
            matchHolder.getMatchAcceptanceEvent().put(matchedUserId, new MatchAcceptanceEvent(matchedUserId, userId, MatchAcceptanceStatus.PENDING));
            System.out.println(userName + " Matched with " + matchedUserName);

        } else {
            String userName = userService.getUsernameById(userId);
            messagingTemplate.convertAndSendToUser(userName, "/queue/matching", -1);  // No user found for match
            findMatch(userId);
        }

    }


    public void handleMatchAcceptance(MatchAcceptanceEvent matchAcceptanceEvent) {
        matchHolder.getMatchAcceptanceEvent().put(matchAcceptanceEvent.getUserId(), matchAcceptanceEvent);
        eventPublisher.publishEvent(matchAcceptanceEvent);
        eventPublisher.publishEvent(1L);
    }

    public void stopMatching(Long userId) {
        matchHolder.getActiveUsers().remove(userId);
        if (matchHolder.getUsersInMatchingState().containsKey(userId)) {
            Long matchedUserId = matchHolder.getUsersInMatchingState().get(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);
        }
    }
}

