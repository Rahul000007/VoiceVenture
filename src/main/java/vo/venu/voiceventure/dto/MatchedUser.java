package vo.venu.voiceventure.dto;

import lombok.Data;
import vo.venu.voiceventure.enums.ProficiencyLevel;

@Data
public class MatchedUser {
    private String userId;
    private String matchedUserId;
    private String name;
    private String matchedUserProfilePicUrl;
    private ProficiencyLevel proficiencyLevel;
    private String rating;
    private String achievement;
}
