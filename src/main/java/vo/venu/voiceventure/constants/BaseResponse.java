package vo.venu.voiceventure.constants;

import lombok.Data;


@Data
public class BaseResponse {
    private String message;
    private int status;
    private Object data;

    public BaseResponse(Object data) {
        this.data = data;
        this.status = 01;
    }

    public BaseResponse(Object data, int status) {
        this.data = data;
        this.status = status;
    }

    public BaseResponse(Object data, String message) {
        this.data = data;
        this.message = message;
        this.status = 01;
    }

    public BaseResponse(String message) {
        this.message = message;
        this.status = 01;
    }

    public BaseResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public BaseResponse(Object data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
