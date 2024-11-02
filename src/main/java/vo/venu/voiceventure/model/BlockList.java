package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class BlockList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The user who is blocking

    @ManyToOne
    @JoinColumn(name = "blocked_user_id")
    private User blockedUser; // The user who is being blocked

    // Block Action Details
    private String blockReason; // Optional reason for blocking
    private Boolean isActive = true; // Is the block currently active?

    // Timestamps
    private LocalDateTime createdAt = LocalDateTime.now(); // When the block was created
    private LocalDateTime updatedAt; // When the block was last updated
}
