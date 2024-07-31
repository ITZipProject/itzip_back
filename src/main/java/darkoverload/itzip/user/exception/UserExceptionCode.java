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
    FILED_ERROR(HttpStatus.BAD_REQUEST, "요청값이 올바르지 않습니다."),
    // 이메일 인증번호 불일치
    NOT_MATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),
    // 이메일 비밀번호 불일치
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "이메일과 비밀번호가 일치하지 않습니다."),
    // 유저를 찾을 수 없음
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String data;
}
