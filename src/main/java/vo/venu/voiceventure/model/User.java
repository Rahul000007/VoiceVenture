package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.ProficiencyLevel;
import vo.venu.voiceventure.enums.Role;
import vo.venu.voiceventure.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic User Information
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String profilePictureUrl;

    // User Role and Permissions - now an Enum
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    //    Language and Learning Preferences
    private String nativeLanguage;
    private String learningLanguage;
    @Enumerated(EnumType.STRING)
    private ProficiencyLevel proficiencyLevel;


    @ManyToMany
    private Set<LanguagePreference> languagePreferences;

    //    Audio Call Settings
    private Boolean isAvailableForCall;
    private LocalDateTime lastCallTime;
    private int callDurationLimit;

    @ManyToMany
    private Set<Topic> preferredTopics;

    //    Social and Interaction Features
    private Boolean isBlocked;
    @ManyToMany
    private Set<User> friends;
    @ManyToMany
    private Set<User> blockedUsers;
    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> receivedFriendRequests;
    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentFriendRequests;
    @OneToMany(mappedBy = "rater")
    private Set<Rating> ratings;

    //    Account and Authentication Information
    private Boolean isEmailVerified;
    private Boolean isPhoneNumberVerified;
    private String jwtToken;
    private String resetPasswordToken;
    @OneToMany(mappedBy = "user")
    private Set<LoginHistory> loginHistory;

    //    Timestamps and Status Information
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    //    Engagement Features
    private int totalCallMinutes;
    private int conversationCount;
    private double averageRating;
    @ElementCollection
    private Set<String> achievements;

}
