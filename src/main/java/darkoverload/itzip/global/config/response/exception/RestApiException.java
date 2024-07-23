package darkoverload.itzip.global.config.response.exception;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
런타입 exception 코드를 확장해서 원하는 에러를 넘길수 있도록 만듬
throw new RestApiException(CommonExceptionCode.BAD_REQUEST) 어디서든 이런 형식으로 예외가 발생하면 예외를 발생시킨다.
 */
@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ResponseCode exceptionCode;
}