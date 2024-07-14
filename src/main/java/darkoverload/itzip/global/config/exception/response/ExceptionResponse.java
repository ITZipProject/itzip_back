package darkoverload.itzip.global.config.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponse {

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationException> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationException {
        private final String field;
        private final String message;

        public static ValidationException of(final FieldError fieldError) {
            return ValidationException.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}