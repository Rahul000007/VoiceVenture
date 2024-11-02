package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.NotificationPriority;
import vo.venu.voiceventure.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Notification Information
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient; // User who receives the notification

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender; //  user who triggered the notification

    // Notification Content
    @Enumerated(EnumType.STRING)
    private NotificationType type; // Type of notification

    private String title; // Title or subject of the notification
    private String message; // Body content of the notification
    private String url; // URL for redirection


    // Status and Timing
    private Boolean isRead = false; // Has the notification been read?
    private LocalDateTime createdAt = LocalDateTime.now(); // When the notification was created
    private LocalDateTime readAt; // When the notification was read (if applicable)
    private LocalDateTime sentAt; // When the notification was sent
    private LocalDateTime expiresAt; // Optional expiration time for the notification

    // Priority and Importance
    @Enumerated(EnumType.STRING)
    private NotificationPriority priority = NotificationPriority.MEDIUM; // Notification priority level

    private Boolean isCritical = false; // Is this a critical notification?

    // Metadata for User Interaction
    private String notificationData; // Optional field for extra metadata (e.g., JSON for extra context)
    private Boolean actionRequired = false; // Does the notification require user action?


}
