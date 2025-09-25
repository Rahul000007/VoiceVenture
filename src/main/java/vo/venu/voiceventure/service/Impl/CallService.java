package vo.venu.voiceventure.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vo.venu.voiceventure.dto.SignalingMessage;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CallService {

    private final Map<String, String> activeCalls = new HashMap<>();

    public void initiateCall(SignalingMessage message) {
        activeCalls.put(message.getSenderId(), message.getReceiverId());
    }

    public void acceptCall(SignalingMessage message) {
        activeCalls.put(message.getReceiverId(), message.getSenderId());
    }

    public void rejectCall(SignalingMessage message) {
        activeCalls.remove(message.getSenderId());
    }

    public void endCall(SignalingMessage message) {
        activeCalls.remove(message.getSenderId());
        activeCalls.remove(message.getReceiverId());
    }

    public String getActiveCall(Long userId) {
        return activeCalls.get(userId);
    }
}
