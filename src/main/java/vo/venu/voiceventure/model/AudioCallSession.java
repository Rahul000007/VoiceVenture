package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.CallStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class AudioCallSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Call Information
    @ManyToOne
    @JoinColumn(name = "caller_id")
    private User caller;  // The user who initiated the cal

    @ManyToOne
    @JoinColumn(name = "callee_id")
    private User callee;  // The user who received the call

    private LocalDateTime callStartTime;
    private LocalDateTime callEndTime;
    private int callDuration; // in seconds

    // Call Status and Quality
    @Enumerated(EnumType.STRING)
    private CallStatus callStatus;
    private int callQualityRating;  // Rating of 1-5
    private String disconnectReason;   // User Disconnected, Network Issue, Timeout


    // Feedback & Conversation Data
    @ManyToOne
    private Topic callTopic;
    private String conversationFeedback;

    // Audio Call Metadata
    private Boolean isRecorded;
    private String recordingUrl;
    private String matchReason;

    // Technical Details
    private String ipAddressCaller;
    private String ipAddressCallee;
    private String networkType;   //WiFi, 4G
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
