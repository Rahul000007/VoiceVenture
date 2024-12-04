package vo.venu.voiceventure.dto;

import lombok.Data;
import vo.venu.voiceventure.enums.ProficiencyLevel;

@Data
public class MatchedUser {
    private Long userId;
    private Long matchedUserId;
    private String username;
    private String email;
    private String fullName;
    private String profilePictureUrl;
    private ProficiencyLevel proficiencyLevel;
    private String rating;
    private String achievement;
}
