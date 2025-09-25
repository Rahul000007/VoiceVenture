package vo.venu.voiceventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;

@Data
@AllArgsConstructor
public class MatchAcceptanceEvent {
    private String userId;
    private String matchedUserId;
    private MatchAcceptanceStatus acceptanceStatus;
}
