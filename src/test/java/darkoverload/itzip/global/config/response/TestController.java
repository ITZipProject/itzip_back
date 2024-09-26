package darkoverload.itzip.global.config.response;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ServiceUnavailableException;
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


    @GetMapping("/success")
    public String successs() {
        return "성공했습니다";
    }

    @GetMapping("/success2")
    public ResponseEntity<Object> successs2() {
        Map<String, String> response = new HashMap<>();
        response.put("field1", "field1Value");
        response.put("field2", "field2Value");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/success3")
    public Map<String, String> successs3() {
        Map<String, String> response = new HashMap<>();
        response.put("field1", "field1Value");
        response.put("field2", "field2Value");
        return response;
    }

    @GetMapping("/clientAbortException")
    public void throwClientAbortException() throws ClientAbortException {
        throw new ClientAbortException("요청 시간이 초과되었습니다");
    }

    @GetMapping("/serviceUnavailable")
    public void throwServiceUnavailableException() throws ServiceUnavailableException {
        throw new ServiceUnavailableException("서비스를 사용할 수 없습니다");
    }

    @PostMapping("/validation")
    public ResponseEntity<String> validateDto(@Valid @RequestBody TestDto dto) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/missingParameter")
    public ResponseEntity<String> validateMissingParameter(@RequestParam String id) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/validateMethod")
    public ResponseEntity<String> validateMethod(@Validated @RequestParam @Min(0) int age) {
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/validateMediaType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> validateMediaType(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/validateFile")
    public ResponseEntity<String> validateFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok("");
    }

    @Getter
    @Setter
    public static class TestDto {

        @NotEmpty(message = "위기를 꺼꾸로 하면 뭔지 아시나요? 기위입니다. 즉 아무것도 아닙니다.")
        private String name;
    }
}