package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.ConversationStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class ConversationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Conversation Information
    @ManyToOne
    @JoinColumn(name = "caller_id")
    private User caller;

    @ManyToOne
    @JoinColumn(name = "callee_id")
    private User callee;

    @ManyToOne
    @JoinColumn(name = "audio_call_session_id")
    private AudioCallSession audioCallSession;

    private LocalDateTime conversationStartTime;
    private LocalDateTime conversationEndTime;
    private int conversationDuration; // in seconds


    // Conversation Content and Quality
    private String conversationNotes; // Notes on the conversation
    @Lob // If the transcript is long, use @Lob
    private String conversationTranscript; // Optional transcript of the conversation
    private int conversationQualityRating; // Rating of 1-5

    // Topics Discussed
    @ManyToMany
    private Set<Topic> discussedTopics;

    // Feedback and Engagement
    private String userFeedback; // General user feedback
    private String callerFeedback; // Caller-specific feedback
    private String calleeFeedback; // Callee-specific feedback

    // Additional Information
    private String recordingUrl; // URL to the recording, if available
    private String languageUsed; // Language used during the conversation
    private Boolean isFavorite; // If marked as favorite by a user

    // Technical Details
    @Enumerated(EnumType.STRING)
    private ConversationStatus conversationStatus;

    // Timestamps (optional, can be useful)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
