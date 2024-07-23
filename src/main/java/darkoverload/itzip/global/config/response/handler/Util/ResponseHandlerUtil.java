package darkoverload.itzip.global.config.response.handler.Util;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import darkoverload.itzip.global.config.response.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

/*
응답의 형식을 같게하는 Utill
응답에는 data가 존재하지 않아도 상관 없기 때문에 data를 받는 걸로만 만들었다.
 */
public class ResponseHandlerUtil {

    /**
     * 성공 응답을 받아오고 응답 형식을 만들게하고 Body에 넣는 메서드
     * @param responseCode 응답 코드를 받아온다.
     * @param data         개발자가 보내주는 data값을 넣어준다.
     */
    public static <T> ResponseEntity<Object> handleSuccess(final ResponseCode responseCode, T data) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(makeSuccessResponse(responseCode, data));
    }

    /**
     * 성공 응답을 받아오고 응답 형식을 만드는 메서드
     * @param responseCode 응답 코드를 받아온다.
     * @param data         개발자가 보내주는 data값을 넣어준다.
     */
    private static <T> SuccessResponse<T> makeSuccessResponse(final ResponseCode responseCode, T data) {
        return SuccessResponse.<T>builder()
                .status(responseCode.getHttpStatus().toString())
                .code(responseCode.name())
                .data(data)
                .msg(responseCode.getData())
                .build();
    }
}