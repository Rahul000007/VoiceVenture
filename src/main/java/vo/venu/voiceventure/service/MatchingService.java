package vo.venu.voiceventure.service;

import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import vo.venu.voiceventure.dto.MatchedUser;
import vo.venu.voiceventure.model.User;

public interface MatchingService {

     void startMatching(String userId);

     void triggerMatching();

     void handleMatchAcceptance(MatchAcceptanceEvent matchAcceptanceEvent);

     void stopMatching(String userId);

     MatchedUser getMatchedUser(User user);
}
