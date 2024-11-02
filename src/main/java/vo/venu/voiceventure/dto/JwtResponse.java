package vo.venu.voiceventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String username;
    private Long userId;
    private List<String> roles;

}
