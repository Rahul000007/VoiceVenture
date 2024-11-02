package vo.venu.voiceventure.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.InteractionType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @ManyToOne
    @JoinColumn(name = "rater_id")
    private User rater; // The user giving the rating

    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser; // The user being rated

    // Rating Details
    private Integer score; // Numeric score (e.g., from 1 to 5)
    private String comments; // Optional feedback or comments

    // Interaction Metadata
    @Enumerated(EnumType.STRING)
    private InteractionType interactionType; // Type of interaction being rated

    private Long interactionId; // ID of the specific interaction being rated
    // Timestamps
    private LocalDateTime createdAt = LocalDateTime.now(); // When the rating was created
    private LocalDateTime updatedAt; // When the rating was last updated

}
