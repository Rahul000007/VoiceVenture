package vo.venu.voiceventure.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vo.venu.voiceventure.dto.GenerateTokenResponse;
import vo.venu.voiceventure.enums.Status;
import vo.venu.voiceventure.model.JwtToken;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.JwtTokenRepository;
import vo.venu.voiceventure.repository.UserRepository;
import vo.venu.voiceventure.service.AccountService;
import vo.venu.voiceventure.util.Constants;
import vo.venu.voiceventure.util.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Value("${jwt.access.validity}")
    private Long refreshTokenValidity;

    private final JwtUtil jwtUtil;
    private final TokenServiceImpl tokenService;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    @Override
    public GenerateTokenResponse generateToken(User user) throws Exception {
        Date tokenExpiry = new Date(System.currentTimeMillis() + refreshTokenValidity);
        Map<String, Object> claims = new HashMap<>();
        String refreshToken = jwtUtil.generateAccessToken(user.getEmail(), claims, tokenExpiry);
        setJwtClaims(user, claims);
        String mainToken = jwtUtil.generateAccessToken(user.getEmail(), claims, tokenExpiry);
        tokenService.inactivePreviousToken(user.getUserId());
        JwtToken jwtToken = new JwtToken();
        jwtToken.setRefreshToken(refreshToken);
        jwtToken.setToken(mainToken);
        jwtToken.setUser(user);
        jwtToken.setStatus(Status.ACTIVE);
        jwtToken.setTokenExpiry(tokenExpiry);
        jwtTokenRepository.saveAndFlush(jwtToken);

        GenerateTokenResponse tokenResponse = new GenerateTokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setJwtToken(mainToken);
        tokenResponse.setExpiry(tokenExpiry);
        return tokenResponse;
    }

    private void setJwtClaims(User user, Map<String, Object> claims) {
        claims.put(Constants.USER_ID, String.valueOf(user.getUserId()));
        claims.put(Constants.USER_MOBILE_NO, user.getMobileNumber());
        claims.put(Constants.USER_EMAIL, user.getEmail());
    }

    @Override
    public void logout(String userId) throws Exception {
        JwtToken previousToken = tokenService.inactivePreviousToken(userId);
        if (ObjectUtils.isEmpty(previousToken)) {
            throw new Exception("Invalid Token");
        }
    }

    @Override
    public void updateToken(String userId) throws Exception {

        Date tokenExpiry = new Date(System.currentTimeMillis() + refreshTokenValidity);

        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE);
        if (ObjectUtils.isEmpty(user)) {
            throw new Exception("User account not found");
        }
        JwtToken jwtToken = jwtTokenRepository.findByUserAndStatus(user, Status.ACTIVE);
        if (ObjectUtils.isEmpty(jwtToken)) {
            throw new Exception("Token is expired");
        }

        Map<String, Object> claims = new HashMap<>();
        setJwtClaims(user, claims);
        String mainToken = jwtUtil.generateAccessToken(String.valueOf(user.getUserId()), claims, tokenExpiry);

        jwtToken.setToken(mainToken);
        jwtTokenRepository.saveAndFlush(jwtToken);
    }
}
