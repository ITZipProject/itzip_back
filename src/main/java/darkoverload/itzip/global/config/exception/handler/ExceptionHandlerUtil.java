package darkoverload.itzip.global.config.exception.handler;

import darkoverload.itzip.global.config.exception.exceptionCode.ExceptionCode;
import darkoverload.itzip.global.config.exception.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerUtil {

    public static ResponseEntity<Object> handleExceptionInternal(final ExceptionCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode));
    }

    public static ResponseEntity<Object> handleExceptionInternal(final ExceptionCode exceptionCode, final String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode, message));
    }

    private static ExceptionResponse makeErrorResponse(final ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .code(exceptionCode.name())
                .message(exceptionCode.getMessage())
                .build();
    }

    private static ExceptionResponse makeErrorResponse(final ExceptionCode exceptionCode, final String message) {
        return ExceptionResponse.builder()
                .code(exceptionCode.name())
                .message(message)
                .build();
    }
}