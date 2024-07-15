package darkoverload.itzip.global.config.response.handler.Util;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import darkoverload.itzip.global.config.response.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerUtil {

    public static ResponseEntity<Object> handleExceptionInternal(final ResponseCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode));
    }

    public static ResponseEntity<Object> handleExceptionInternal(final ResponseCode exceptionCode, final String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode, message));
    }

    private static ExceptionResponse makeErrorResponse(final ResponseCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(exceptionCode.getData())
                .build();
    }

    private static ExceptionResponse makeErrorResponse(final ResponseCode exceptionCode, final String message) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(message)
                .build();
    }
}