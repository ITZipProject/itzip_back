package darkoverload.itzip.global.config.response.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    String name();
    HttpStatus getHttpStatus();
    String getData();
}
