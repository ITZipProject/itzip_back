package darkoverload.itzip.jwt.exception;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import lombok.Getter;

@Getter
public class CustomJwtException extends RuntimeException {
    private final TokenExceptionCode tokenExceptionCode;

    public CustomJwtException(TokenExceptionCode tokenExceptionCode) {
        super(tokenExceptionCode.getData());
        this.tokenExceptionCode = tokenExceptionCode;
    }
}
