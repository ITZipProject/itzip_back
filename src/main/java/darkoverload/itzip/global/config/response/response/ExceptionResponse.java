package darkoverload.itzip.global.config.response.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

/*
예외 형식을 지정해논 클래스
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponse {

    //예외 상태
    private final String status;
    //예외 코드
    private final String code;
    //예외 내용
    private final Object data;

    //검증 예외 목록 Valid를 사용하는 경우 사용되나 현제는 사용되지 않음
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationException> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationException {
        private final String status;
        private final String field;
        private final Object data;

        /**
         * FieldError 객체를 ValidationException 객체로 변환하는 메서드
         * @param fieldError FieldError 객체
         * @return 변환된 ValidationException 객체
         */
        public static ValidationException of(final FieldError fieldError) {
            return ValidationException.builder()
                    .status("400 Bad Request")
                    .field(fieldError.getField())
                    .data(fieldError.getDefaultMessage())
                    .build();
        }
    }
}