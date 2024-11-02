package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.FriendRequestStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Request Information
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    // Status and Timing
    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    private LocalDateTime sentAt;
    private LocalDateTime respondedAt;
    private LocalDateTime expirationDate;

    // Request Metadata
    private String message; // message from sender
    private Boolean isRead; // Has the receiver read the request?
    private Boolean notificationSent; // Was a notification sent to the receiver?

    // Rejection or Cancellation Information
    private String rejectionReason; //  reason for rejection
    private String cancellationReason; // reason for canceling

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
