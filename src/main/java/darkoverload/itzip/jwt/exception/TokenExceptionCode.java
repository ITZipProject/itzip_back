package darkoverload.itzip.jwt.exception;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 토큰 예외 코드
 */
@Getter
@RequiredArgsConstructor
public enum TokenExceptionCode implements ResponseCode {
    // TOKEN 만료
    JWT_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    // Token 오류
    JWT_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    // 토큰 오류
    JWT_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    // 변조된 토큰
    JWT_UNSUPPORTED_ERROR(HttpStatus.UNAUTHORIZED, "변조된 토큰입니다."),
    // 알 수 없는 오류
    JWT_INTERNAL_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String data;
}
