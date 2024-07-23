package darkoverload.itzip.global.config.response.handler.Util;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import darkoverload.itzip.global.config.response.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;

/*
예외 형식을 지정하는 Utill
코드를 작성할 때 ResatApiException을 만들도록 해서 이걸 사용할 일은 없다.
 */
public class ExceptionHandlerUtil {

    //메세지를 주지 않은 에러 코드를 설정
    public static ResponseEntity<Object> handleExceptionInternal(final ResponseCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode));
    }

    //메세지를 준 에러 코드를 설정
    public static ResponseEntity<Object> handleExceptionInternal(final ResponseCode exceptionCode, final String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode, message));
    }

    //메세지를 주지 않은 에러코드의 응답 형식을 만듬 exceptionCode안에 들어있는 기본 msg를 data에 넣어준다.
    private static ExceptionResponse makeErrorResponse(final ResponseCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(exceptionCode.getData())
                .build();
    }

    //메세지를 준 에러코드의 응답 형식을 만듬 data에 message가 들어간다.
    private static ExceptionResponse makeErrorResponse(final ResponseCode exceptionCode, final String message) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(message)
                .build();
    }
}