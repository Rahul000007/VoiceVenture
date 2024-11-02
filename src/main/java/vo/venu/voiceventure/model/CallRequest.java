package vo.venu.voiceventure.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import vo.venu.voiceventure.enums.RequestStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class CallRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @ManyToOne
    @JoinColumn(name = "caller_id")
    private User caller; // User who initiated the call

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver; // User who receives the call request

    // Request Metadata
    private LocalDateTime requestTime; // When the request was sent
    private LocalDateTime expirationTime; // When the request expires

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // Status of the request
}
