package vo.venu.voiceventure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vo.venu.voiceventure.constants.BaseResponse;
import vo.venu.voiceventure.constants.RestMapping;
import vo.venu.voiceventure.dto.LoginDTO;
import vo.venu.voiceventure.dto.SignUpDTO;
import vo.venu.voiceventure.service.AccountService;
import vo.venu.voiceventure.service.UserService;
import vo.venu.voiceventure.util.AuthServiceUtil;

@RestController()
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final AccountService accountService;

    @PostMapping(path = RestMapping.REGISTER_USER)
    public ResponseEntity<BaseResponse> registerUser(@RequestBody SignUpDTO request) throws Exception {
        return new ResponseEntity<>(new BaseResponse(userService.registerUser(request),"Registration successful"), HttpStatus.CREATED);
    }

    @PostMapping(path = RestMapping.LOGIN)
    public ResponseEntity<BaseResponse> login(@RequestBody LoginDTO request) throws Exception {
        return new ResponseEntity<>(new BaseResponse(userService.login(request)), HttpStatus.OK);
    }

    @GetMapping(path = RestMapping.LOGOUT)
    public ResponseEntity<BaseResponse> logout() throws Exception {
        accountService.logout(AuthServiceUtil.getUserId());
        return new ResponseEntity<>(new BaseResponse("Logout successfully"), HttpStatus.OK);
    }

}
