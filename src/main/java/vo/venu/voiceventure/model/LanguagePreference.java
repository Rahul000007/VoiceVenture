package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.ProficiencyLevel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class LanguagePreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The user associated with this preference

    // Language Details
    private String language; // The language name

    @Enumerated(EnumType.STRING)
    private ProficiencyLevel proficiencyLevel; // User's proficiency level in this language

    // Preference Settings
    private Boolean isPrimary = false; // Is this the primary language preference?
    private Boolean isActive = true; // Is this preference currently active?

    // Additional Metadata
    private LocalDateTime createdAt = LocalDateTime.now(); // When the preference was created
    private LocalDateTime updatedAt; // When the preference was last updated
}