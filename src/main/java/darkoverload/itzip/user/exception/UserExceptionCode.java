package darkoverload.itzip.user.exception;

import darkoverload.itzip.global.config.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements ResponseCode {
    // 사용 중인 이메일
    EXIST_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
    // 올바르지 않은 요청값
    FILED_ERROR(HttpStatus.BAD_REQUEST, "요청값이 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String data;
}
