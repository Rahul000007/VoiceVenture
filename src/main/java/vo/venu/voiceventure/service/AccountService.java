package vo.venu.voiceventure.service;


import vo.venu.voiceventure.dto.GenerateTokenResponse;
import vo.venu.voiceventure.model.User;

public interface AccountService {

    GenerateTokenResponse generateToken(User user) throws Exception;

    void logout(String userId) throws Exception;

    void updateToken(String userId) throws Exception;
}