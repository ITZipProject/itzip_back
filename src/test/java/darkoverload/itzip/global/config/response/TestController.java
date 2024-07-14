package darkoverload.itzip.global.config.response;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.response.exceptionCode.CommonExceptionCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/restApiException")
    public void throwRestApiException() {
        throw new RestApiException(CommonExceptionCode.BAD_REQUEST);
    }

    @GetMapping("/restApiException2")
    public void throwRestApiException2() {
        throw new RestApiException(CommonExceptionCode.UNAUTHORIZED);
    }

    @GetMapping("/restApiException3")
    public void throwRestApiException3() {
        throw new RestApiException(CommonExceptionCode.FORBIDDEN);
    }

    @GetMapping("/restApiException4")
    public void throwRestApiException4() {
        throw new RestApiException(CommonExceptionCode.NOT_FOUND);
    }

    @GetMapping("/illegalArgumentException")
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("잘못된 인자입니다");
    }

    @GetMapping("/illegalArgumentException2")
    public void throwIllegalArgumentException2(@RequestParam String param) {
    }

    @GetMapping("/unsupportedOperationException")
    public void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("지원되지 않는 작업입니다");
    }

    @GetMapping("/unsupportedOperationException2")
    public void throwUnsupportedOperationException2() {
        List<String> singletonList = Collections.singletonList("item");
        singletonList.remove(0);
    }

    @GetMapping("/exception")
    public void throwException() {
        throw new RuntimeException("내부 서버 오류입니다");
    }

    @GetMapping("/exception2")
    public void throwException2() {
        String str = null;
        str.length();
    }
}