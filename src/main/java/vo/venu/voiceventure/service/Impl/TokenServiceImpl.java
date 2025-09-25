package vo.venu.voiceventure.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vo.venu.voiceventure.enums.Status;
import vo.venu.voiceventure.model.JwtToken;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.JwtTokenRepository;
import vo.venu.voiceventure.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl {

    @Value("${jwt.access.validity}")
    private Long accessTokenValidity;

    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    public JwtToken inactivePreviousToken(String userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE);

        JwtToken previousToken = jwtTokenRepository.findByUserAndStatus(user, Status.ACTIVE);
        if (!ObjectUtils.isEmpty(previousToken)) {
            previousToken.setStatus(Status.INACTIVE);
            jwtTokenRepository.saveAndFlush(previousToken);
        }
        return previousToken;
    }

}
