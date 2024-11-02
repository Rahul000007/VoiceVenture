package vo.venu.voiceventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.SignalingMessageType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignalingMessage {

    private Long senderId;          // ID of the user sending the message
    private Long receiverId;        // ID of the target user
    private Object payload;         // Main content (SDP, ICE candidate)
    private SignalingMessageType type; // The type of signaling message (e.g., OFFER, ANSWER, ICE, CALL_REQUEST)

}
