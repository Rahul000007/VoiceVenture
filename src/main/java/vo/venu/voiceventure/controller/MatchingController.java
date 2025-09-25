package vo.venu.voiceventure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.venu.voiceventure.constants.BaseResponse;
import vo.venu.voiceventure.constants.RestMapping;
import vo.venu.voiceventure.service.MatchingService;
import vo.venu.voiceventure.util.AuthServiceUtil;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping(RestMapping.START_MATCHING)
    public ResponseEntity<BaseResponse> startMatching() {
        matchingService.startMatching(AuthServiceUtil.getUserId());
        return new ResponseEntity<>(new BaseResponse("Matching started"), HttpStatus.OK);
    }

    @GetMapping(RestMapping.STOP_MATCHING)
    public ResponseEntity<BaseResponse> stopMatching() {
        matchingService.stopMatching(AuthServiceUtil.getUserId());
        return new ResponseEntity<>(new BaseResponse("Matching Stopped"), HttpStatus.OK);
    }

}

