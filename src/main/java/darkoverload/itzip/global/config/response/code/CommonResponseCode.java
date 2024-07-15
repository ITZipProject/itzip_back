package darkoverload.itzip.global.config.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonResponseCode implements ResponseCode {
    SUCCESS(HttpStatus.OK, "요청이 성공됐습니다");

    private final HttpStatus httpStatus;
    private final String data;
}
