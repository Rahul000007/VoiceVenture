package vo.venu.voiceventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallSession {
    private String callerId;
    private String receiverId;
    private boolean callAccepted;

    public CallSession(String callerId, String receiverId) {
        this.callerId = callerId;
        this.receiverId = receiverId;
        this.callAccepted = false;
    }
}
