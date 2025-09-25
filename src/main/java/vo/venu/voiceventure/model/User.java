package vo.venu.voiceventure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vo.venu.voiceventure.enums.ProficiencyLevel;
import vo.venu.voiceventure.enums.Role;
import vo.venu.voiceventure.enums.Status;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "mobile_number", unique = true, nullable = true, length = 15)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "native_language")
    private String nativeLanguage;

    @Column(name = "learning_language")
    private String learningLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level")
    private ProficiencyLevel proficiencyLevel;

    @Column(name = "available_for_call")
    private Boolean availableForCall;

    @Column(name = "last_call_time")
    private LocalDateTime lastCallTime;

    @ManyToMany
    @Column(name = "friends")
    private Set<User> friends;

    @ManyToMany
    @Column(name = "blocked_user")
    private Set<User> blockedUsers;

    @OneToMany(mappedBy = "receiver")
    @Column(name = "received_friend_request")
    private Set<FriendRequest> receivedFriendRequests;

    @OneToMany(mappedBy = "sender")
    @Column(name = "sent_friend_request")
    private Set<FriendRequest> sentFriendRequests;

    @Column(name = "total_call_minutes")
    private Integer totalCallMinutes;

    @Column(name = "conversation_count")
    private Integer conversationCount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
}
