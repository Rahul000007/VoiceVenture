package vo.venu.voiceventure.service;

import vo.venu.voiceventure.dto.LoginDTO;
import vo.venu.voiceventure.dto.RegisterTokenDTO;
import vo.venu.voiceventure.dto.SignUpDTO;

public interface UserService {
    RegisterTokenDTO registerUser(SignUpDTO signUpDTO) throws Exception;

    RegisterTokenDTO login(LoginDTO request) throws Exception;

    String getNameByUserId(String userId);

    String getUserNameByUserId(String userId);
}
