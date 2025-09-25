package vo.venu.voiceventure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.ObjectUtils;

@Slf4j
public class AuthServiceUtil {

    private static final String USER_ID = "userId";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_MOBILE_NO = "userMobileNo";

    public static Object getClaim(String parameterName) {
        Jwt jwt = getJwtFromSecurityContext();
        if (jwt != null) {
            return jwt.getClaim(parameterName);
        }
        log.warn("JWT not found for claim '{}' is missing", parameterName);
        return null;
    }

    private static Jwt getJwtFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt) {
            return (Jwt) principal;
        }
        log.warn("No JWT found in SecurityContext");
        return null;
    }

    public static String getUserEmail() {
        return (String) getClaim(USER_EMAIL);
    }

    public static String getUserId() {
        return (String) getClaim(USER_ID);
    }

    public static String getUserMobileNo() {
        return (String) getClaim(USER_MOBILE_NO);
    }

    public static String getLogUserId() {
        try {
            Jwt jwt = null;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof Jwt) {
                jwt = (Jwt) principal;
            }
            if (!ObjectUtils.isEmpty(jwt)) {
                String userId = jwt.getClaim(USER_ID);
                return ObjectUtils.isEmpty(userId) ? "" : userId;
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
