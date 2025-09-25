package vo.venu.voiceventure.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GenerateTokenResponse {
    private String jwtToken;
    private String refreshToken;
    private Date expiry;
}
