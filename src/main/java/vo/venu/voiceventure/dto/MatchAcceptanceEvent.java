package vo.venu.voiceventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import vo.venu.voiceventure.enums.MatchAcceptanceStatus;

@Data
@AllArgsConstructor
public class MatchAcceptanceEvent {
    private Long userId;
    private Long matchedUserId;
    private MatchAcceptanceStatus acceptanceStatus;
}
