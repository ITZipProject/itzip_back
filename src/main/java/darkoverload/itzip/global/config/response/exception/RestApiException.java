package darkoverload.itzip.global.config.response.exception;

import darkoverload.itzip.global.config.response.exceptionCode.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ExceptionCode exceptionCode;
}
