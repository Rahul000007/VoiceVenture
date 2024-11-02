package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.TopicCategory;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Topic Information
    private String title;
    private String description;

    // Categorization and Hierarchy
    @Enumerated(EnumType.STRING)
    private TopicCategory category;

    @ManyToOne
    @JoinColumn(name = "parent_topic_id")
    private Topic parentTopic; // Parent topic if hierarchical

    @OneToMany(mappedBy = "parentTopic")
    private Set<Topic> subTopics; // Subtopics

    // Language and Regional Relevance
    private String language; // Language for the topic
    private Boolean isGlobal; // Is this a global topic or region-specific?

    // User Interaction and Popularity
    private int popularityScore; // Popularity ranking based on usage
    private int totalConversations; // Total number of conversations using this topic

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy; // Optional field, if topics can be created by users

    // Additional Information
    private Boolean isActive; // Is the topic currently active?
    private String iconUrl; // URL for an icon representing the topic

}
