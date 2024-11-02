package vo.venu.voiceventure.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The user who logged in

    // Login Details
    private LocalDateTime loginTimestamp; // When the user logged in
    private LocalDateTime logoutTimestamp; // When the user logged out (if applicable)
    private String ipAddress; // IP address of the user
    private String deviceInfo; // Information about the device used

    // Session Metadata
    private String sessionId; // Unique identifier for the user session
    private Boolean wasSuccessful; // Was the login successful?
    private String loginMethod; // Method of login (e.g., EMAIL_PASSWORD, SOCIAL_LOGIN)

    // Timestamps
    private LocalDateTime createdAt = LocalDateTime.now(); // When the history entry was created
    private LocalDateTime updatedAt; // When the entry was last updated
}
