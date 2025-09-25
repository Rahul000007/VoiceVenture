package vo.venu.voiceventure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import vo.venu.voiceventure.dto.SignalingMessage;
import vo.venu.voiceventure.service.Impl.CallService;
import vo.venu.voiceventure.service.UserService;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;

@Controller
@Slf4j
public class SignalingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private CallService callService;
    @Autowired
    private UserService userService;
    @Autowired
    private MatchHolder matchHolder;

    @MessageMapping("/signal")
    @SendTo("/topic/response")
    public void handleSignal(SignalingMessage signalingMessage) {
        System.out.println("S-H => " + matchHolder.toString());

        switch (signalingMessage.getType()) {
            case CALL_REQUEST:
                handleCallRequest(signalingMessage);
                break;
            case OFFER:
                handleOffer(signalingMessage);
                break;
            case ANSWER:
                handleAnswer(signalingMessage);
                break;
            case ICE_CANDIDATE:
                handleIceCandidate(signalingMessage);
                break;
            case CALL_ACCEPT:
                handleCallAccept(signalingMessage);
                break;
            case CALL_REJECT:
                handleCallReject(signalingMessage);
                break;
            case CALL_END:
                handleCallEnd(signalingMessage);
                break;
            default:
                log.warn("Unknown message type: {}", signalingMessage.getType());
        }
    }

    private void handleCallRequest(SignalingMessage signalingMessage) {
        callService.initiateCall(signalingMessage);
        sendToUser(signalingMessage.getReceiverId(), "/queue/call-request", signalingMessage);
    }

    private void handleOffer(SignalingMessage signalingMessage) {
        sendToUser(signalingMessage.getReceiverId(), "/queue/offer", signalingMessage);
    }

    private void handleAnswer(SignalingMessage signalingMessage) {
        sendToUser(signalingMessage.getReceiverId(), "/queue/answer", signalingMessage);
    }

    private void handleIceCandidate(SignalingMessage signalingMessage) {
        sendToUser(signalingMessage.getReceiverId(), "/queue/ice-candidate", signalingMessage);
    }

    private void handleCallAccept(SignalingMessage signalingMessage) {
        callService.acceptCall(signalingMessage);
        sendToUser(signalingMessage.getReceiverId(), "/queue/call-accept", signalingMessage);
    }

    private void handleCallReject(SignalingMessage signalingMessage) {
        callService.rejectCall(signalingMessage);
        sendToUser(signalingMessage.getReceiverId(), "/queue/call-reject", signalingMessage);
    }

    private void handleCallEnd(SignalingMessage signalingMessage) {
        callService.endCall(signalingMessage);
        sendToUser(signalingMessage.getReceiverId(), "/queue/call-end", signalingMessage);
    }

    private void sendToUser(String id, String destination, SignalingMessage signalingMessage) {
        String username = userService.getNameByUserId(id);
        System.out.println("Sent To " + username + " " + signalingMessage.getType());
        messagingTemplate.convertAndSendToUser(username, destination, signalingMessage);
    }
}
