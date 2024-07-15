package darkoverload.itzip.global.config.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements ResponseCode {
    /**
     * 4** client
     */
    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 매개 변수가 포함됨"),
    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다"),
    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),
    // 404
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다"),
    // 408
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과되었습니다"),
    // 415
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입입니다"),

    /**
     * 5** Server Error
     */
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다"),
    // 502
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "잘못된 게이트웨이입니다"),
    // 503
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "서비스를 사용할 수 없습니다"),
    // 504
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "게이트웨이 시간 초과입니다"),
    // 505
    HTTP_VERSION_NOT_SUPPORTED(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "HTTP 버전을 지원하지 않습니다"),
    // 507
    INSUFFICIENT_STORAGE(HttpStatus.INSUFFICIENT_STORAGE, "저장 공간이 부족합니다"),
    // 511
    NETWORK_AUTHENTICATION_REQUIRED(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "네트워크 인증이 필요합니다");

    private final HttpStatus httpStatus;
    private final String data;
}
