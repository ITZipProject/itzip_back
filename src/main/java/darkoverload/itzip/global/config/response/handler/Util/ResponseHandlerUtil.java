package darkoverload.itzip.global.config.response.handler.Util;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import darkoverload.itzip.global.config.response.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public class ResponseHandlerUtil {

    public static <T> ResponseEntity<Object> handleSuccess(final ResponseCode responseCode, T data) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(makeSuccessResponse(responseCode, data));
    }

    private static <T> SuccessResponse<T> makeSuccessResponse(final ResponseCode responseCode, T data) {
        return SuccessResponse.<T>builder()
                .status(responseCode.getHttpStatus().toString())
                .msg(responseCode.getData())
                .data(data)
                .build();
    }
}