package vo.venu.voiceventure.constants;

public class RestMapping {

    public static final String BASE_URL = "/api/v1";
    public static final String REGISTER_USER = BASE_URL + "/register";
    public static final String LOGIN = BASE_URL + "/login";
    public static final String LOGOUT = BASE_URL + "/logout";
    public static final String MATCHING = BASE_URL + "/matching";
    public static final String START_MATCHING = MATCHING + "/start";
    public static final String STOP_MATCHING = MATCHING + "/stop";


    public RestMapping() {
    }
}
