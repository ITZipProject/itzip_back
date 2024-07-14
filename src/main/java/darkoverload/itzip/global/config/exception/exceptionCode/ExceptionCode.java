package darkoverload.itzip.global.config.exception.exceptionCode;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
