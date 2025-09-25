package vo.venu.voiceventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTokenDTO {
    private String accessToken;
    private String refreshToken;
    private Date expiry;
    private String userId;
    private String name;
    private String profilePic;
}
