package vo.venu.voiceventure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import vo.venu.voiceventure.dto.*;
import vo.venu.voiceventure.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.venu.voiceventure.sessionmgmt.MatchHolder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MatchHolder holder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        holder.getActiveUsers().add(jwtResponse.getUserId());
//        holder.getUsersInMatchingState().put(jwtResponse.getUserId(), jwtResponse.getUserId()+10);
//        holder.getMatchAcceptanceEvent().put(jwtResponse.getUserId(), new MatchAcceptanceEvent(jwtResponse.getUserId(), jwtResponse.getUserId()+10, MatchAcceptanceStatus.ACCEPTED));
//        System.out.println(holder.toString());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtResponse jwtResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset link sent");
    }
}
