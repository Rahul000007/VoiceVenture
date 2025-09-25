package vo.venu.voiceventure.service.Impl;

import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vo.venu.voiceventure.dto.GenerateTokenResponse;
import vo.venu.voiceventure.dto.LoginDTO;
import vo.venu.voiceventure.dto.RegisterTokenDTO;
import vo.venu.voiceventure.dto.SignUpDTO;
import vo.venu.voiceventure.enums.Role;
import vo.venu.voiceventure.enums.Status;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.UserRepository;
import vo.venu.voiceventure.service.AccountService;
import vo.venu.voiceventure.service.UserService;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    @Override
    public RegisterTokenDTO registerUser(SignUpDTO request) throws Exception {
        User user = new User();
        user.setFirstName(request.getName());
        if (StringUtil.isEmpty(request.getEmail())) {
            log.error("Email is empty");
            throw new Exception("Email is empty");
        }
        user.setEmail(request.getEmail());
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        user.setPassword(request.getPassword());
        user.setUserId(String.valueOf(UUID.randomUUID()));
        userRepository.saveAndFlush(user);
        GenerateTokenResponse generateTokenResponse = accountService.generateToken(user);
        RegisterTokenDTO response = new RegisterTokenDTO();
        response.setAccessToken(generateTokenResponse.getJwtToken());
        response.setRefreshToken(generateTokenResponse.getRefreshToken());
        return response;
    }

    @Override
    public RegisterTokenDTO login(LoginDTO request) throws Exception {

        if (StringUtil.isBlank(request.getEmail())) {
            log.error("Email not found");
            throw new Exception("Email not found");
        }

        if (StringUtil.isBlank(request.getPassword())) {
            log.error("Password not found");
            throw new Exception("Password not found");
        }

        User user = userRepository.findByUserNameAndStatus(request.getEmail(), Status.ACTIVE);
        if (ObjectUtils.isEmpty(user)) {
            log.error("User not found");
            throw new Exception("User not found");
        }

        if (!request.getPassword().equals(user.getPassword())) {
            log.error("Incorrect password");
            throw new Exception("Incorrect password");
        }
        GenerateTokenResponse generateTokenResponse = accountService.generateToken(user);
        RegisterTokenDTO response = new RegisterTokenDTO();
        response.setAccessToken(generateTokenResponse.getJwtToken());
        response.setRefreshToken(generateTokenResponse.getRefreshToken());
        response.setExpiry(generateTokenResponse.getExpiry());
        response.setUserId(user.getUserId());
        response.setName(user.getFirstName());
        response.setProfilePic(user.getProfilePictureUrl());
        return response;
    }

    @Override
    public String getNameByUserId(String userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE);
        return user.getFirstName();
    }

    @Override
    public String getUserNameByUserId(String userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE);
        return user.getEmail();
    }
}
