package vo.venu.voiceventure.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.dto.MatchedUser;
import vo.venu.voiceventure.dto.StartMatchingEvent;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.UserRepository;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;
import java.util.*;

@Slf4j
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
    @Autowired
    private UserRepository userRepository;


    public void startMatching(Long userId) {
        eventPublisher.publishEvent(new StartMatchingEvent(userId));
    }

    public void triggerMatching() {
        List<Long> activeUsers = new ArrayList<>(matchHolder.getActiveUsers());


        Collections.shuffle(activeUsers);

        for (int i = 0; i < activeUsers.size(); i += 2) {
            Long userId = activeUsers.get(i);
            Long matchedUserId = activeUsers.get(i + 1);

            String userName = userService.getUsernameById(userId);
            String matchedUserName = userService.getUsernameById(matchedUserId);


            MatchedUser userMatchedFor = getMatchedUser(userRepository.findById(userId).get());
            userMatchedFor.setUserId(matchedUserId);

            MatchedUser matchedUser = getMatchedUser(userRepository.findById(matchedUserId).get());
            matchedUser.setUserId(userId);


            messagingTemplate.convertAndSendToUser(userName, "/queue/matching", matchedUser);
            messagingTemplate.convertAndSendToUser(matchedUserName, "/queue/matching", userMatchedFor);

            matchHolder.getActiveUsers().remove(userId);
            matchHolder.getActiveUsers().remove(matchedUserId);

            matchHolder.getUsersInMatchingState().put(userId, matchedUserId);
            matchHolder.getUsersInMatchingState().put(matchedUserId, userId);

            matchHolder.getMatchAcceptanceEvent().put(userId, new MatchAcceptanceEvent(userId, matchedUserId, MatchAcceptanceStatus.PENDING));
            matchHolder.getMatchAcceptanceEvent().put(matchedUserId, new MatchAcceptanceEvent(matchedUserId, userId, MatchAcceptanceStatus.PENDING));

            matchHolder.getIsCaller().put(userId, false);
            matchHolder.getIsCaller().put(matchedUserId, false);

            log.info("{} Matched with {}", userName, matchedUserName);
        }
    }

    public void handleMatchAcceptance(MatchAcceptanceEvent matchAcceptanceEvent) {
        matchHolder.getMatchAcceptanceEvent().put(matchAcceptanceEvent.getUserId(), matchAcceptanceEvent);
        Boolean isMatchedUserCaller = matchHolder.getIsCaller().get(matchAcceptanceEvent.getMatchedUserId());

        if (matchAcceptanceEvent.getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED) && !isMatchedUserCaller) {
            matchHolder.getIsCaller().put(matchAcceptanceEvent.getUserId(), true);
            log.info("{} is Caller", userService.getUsernameById(matchAcceptanceEvent.getUserId()));
        }
        eventPublisher.publishEvent(matchAcceptanceEvent);
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

    public static MatchedUser getMatchedUser(User user) {
        MatchedUser matchedUser = new MatchedUser();
        matchedUser.setMatchedUserId(user.getId());
        matchedUser.setUsername(user.getUsername());
        matchedUser.setEmail(user.getEmail());
        matchedUser.setFullName(user.getFullName());
        matchedUser.setProfilePictureUrl(user.getProfilePictureUrl());
        matchedUser.setProficiencyLevel(user.getProficiencyLevel());
        return matchedUser;

    }
}

