package darkoverload.itzip.global.config.exception.exception;

import darkoverload.itzip.global.config.exception.exceptionCode.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ExceptionCode exceptionCode;
}
