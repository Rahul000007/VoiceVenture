package vo.venu.voiceventure.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.dto.MatchedUser;
import vo.venu.voiceventure.dto.StartMatchingEvent;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;
import vo.venu.voiceventure.enums.Status;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.UserRepository;
import vo.venu.voiceventure.service.MatchingService;
import vo.venu.voiceventure.service.UserService;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final MatchHolder matchHolder;
    private final UserRepository userRepository;

    @Override
    public void startMatching(String userId) {
        eventPublisher.publishEvent(new StartMatchingEvent(userId));
    }

    @Override
    public void triggerMatching() {
        List<String> activeUsers = new ArrayList<>(matchHolder.getActiveUsers());


        Collections.shuffle(activeUsers);

        for (int i = 0; i < activeUsers.size(); i += 2) {
            String userId = activeUsers.get(i);
            String matchedUserId = activeUsers.get(i + 1);

            String userName = userService.getUserNameByUserId(userId);
            String matchedUserName = userService.getUserNameByUserId(matchedUserId);


            MatchedUser userMatchedFor = getMatchedUser(userRepository.findByUserIdAndStatus(userId,Status.ACTIVE));
            userMatchedFor.setUserId(matchedUserId);

            MatchedUser matchedUser = getMatchedUser(userRepository.findByUserIdAndStatus(matchedUserId,Status.ACTIVE));
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

    @Override
    public void handleMatchAcceptance(MatchAcceptanceEvent matchAcceptanceEvent) {
        matchHolder.getMatchAcceptanceEvent().put(matchAcceptanceEvent.getUserId(), matchAcceptanceEvent);
        Boolean isMatchedUserCaller = matchHolder.getIsCaller().get(matchAcceptanceEvent.getMatchedUserId());

        if (matchAcceptanceEvent.getAcceptanceStatus().equals(MatchAcceptanceStatus.ACCEPTED) && !isMatchedUserCaller) {
            matchHolder.getIsCaller().put(matchAcceptanceEvent.getUserId(), true);
            log.info("{} is Caller", userService.getNameByUserId(matchAcceptanceEvent.getUserId()));
        }
        eventPublisher.publishEvent(matchAcceptanceEvent);
    }

    @Override
    public void stopMatching(String userId) {
        matchHolder.getActiveUsers().remove(userId);
        if (matchHolder.getUsersInMatchingState().containsKey(userId)) {
            String matchedUserId = matchHolder.getUsersInMatchingState().get(userId);
            matchHolder.getUsersInMatchingState().remove(matchedUserId);
            matchHolder.getMatchAcceptanceEvent().remove(userId);
            matchHolder.getMatchAcceptanceEvent().remove(matchedUserId);
        }
    }

    @Override
    public MatchedUser getMatchedUser(User user) {
        MatchedUser matchedUser = new MatchedUser();
        matchedUser.setMatchedUserId(user.getUserId());
        matchedUser.setName(user.getFirstName());
        matchedUser.setMatchedUserProfilePicUrl(user.getProfilePictureUrl());
        matchedUser.setProficiencyLevel(user.getProficiencyLevel());
        return matchedUser;

    }
}

