package darkoverload.itzip.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토큰 예외 코드
 */
@Getter
@AllArgsConstructor
public enum TokenExceptionCode {
    // TOKEN 만료
    JWT_EXPIRED_ERROR(401, "토큰이 만료되었습니다."),
    // Token 오류
    JWT_INVALID_ERROR(401, "토큰이 유효하지 않습니다."),
    // 토큰 오류
    JWT_UNKNOWN_ERROR(401, "토큰이 존재하지 않습니다."),
    // 변조된 토큰
    JWT_UNSUPPORTED_ERROR(401, "변조된 토큰입니다."),
    // 알 수 없는 오류
    JWT_INTERNAL_ERROR(401, "알 수 없는 오류가 발생했습니다.");

    @Getter
    private int code;    // 예외 코드
    @Getter
    private String data; // 예외 메시지
}
