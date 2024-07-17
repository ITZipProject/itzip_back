package darkoverload.itzip.image.exception;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import lombok.Getter;

@Getter
public class CustomImageException extends RuntimeException{
    private final CommonExceptionCode exceptionCode;

    public CustomImageException(CommonExceptionCode exceptionCode) {
        super(exceptionCode.getData());
        this.exceptionCode = exceptionCode;
    }
}

