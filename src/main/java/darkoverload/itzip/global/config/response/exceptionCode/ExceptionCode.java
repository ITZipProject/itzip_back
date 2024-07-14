package darkoverload.itzip.global.config.response.exceptionCode;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
