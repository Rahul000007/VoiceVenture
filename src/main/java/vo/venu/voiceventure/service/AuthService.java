package vo.venu.voiceventure.service;

import org.springframework.beans.factory.annotation.Autowired;
import vo.venu.voiceventure.dto.JwtResponse;
import vo.venu.voiceventure.dto.LoginRequest;
import vo.venu.voiceventure.dto.RegisterRequest;
import vo.venu.voiceventure.dto.RefreshTokenRequest;
import vo.venu.voiceventure.dto.PasswordResetRequest;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import vo.venu.voiceventure.security.UserDetailsImpl;
import vo.venu.voiceventure.security.jwt.JwtProvider;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtProvider jwtProvider;

    public void registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        User user =userDetails.getUser();
        String jwt = jwtProvider.generateJwtToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        return new JwtResponse(jwt, refreshToken, user.getUsername(),user.getId(), List.of(user.getRoles().toString()));
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String token = jwtProvider.refreshToken(refreshTokenRequest.getRefreshToken());
        return new JwtResponse(token, refreshTokenRequest.getRefreshToken(), null,null, List.of());
    }

    public void resetPassword(PasswordResetRequest request) {
    }
}
